package app.sales.details;

import app.RootLogger;
import app.common.BasicEventArgs;
import app.common.details.EditorPanel;
import app.common.validation.ValidationEventArgs;
import app.common.validation.ValidationSummaryPanel;
import app.objectComposition.ServiceLocator;
import app.sales.details.wizard.*;
import core.ItemFactoryProvider;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;
import core.validation.model.ValidationException;
import data.CarDealRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CarDealWizardEditorPanel extends EditorPanel
        implements CarDealWizardEventListener,
        CarDealWizardNavigationEventListener {

    private static final Logger log = RootLogger.get();
    private final CarDealRepository carDetailsRepository;
    private final ItemFactoryProvider<CarDealDetails, CarDealFactory> carDealFactoryProvider;
    private final ValidationSummaryPanel validationMessagesPanel;
    private final CarDealWizardNavigationPanel navigationPanel;
    private final CarDealWizardHeaderPanel headerPanel;
    private final CarDealWizardStepsProvider wizardStepsProvider;
    private final CardLayout contentPresenter;
    private final JPanel contentPanel;

    private CarDealWizard wizard;

    public CarDealWizardEditorPanel() {
        setLayout(new BorderLayout());
        // TODO: replace locator with Constructor Injection
        this.carDetailsRepository = ServiceLocator.getComposer().getCarDealRepository();
        this.carDealFactoryProvider = ServiceLocator.getComposer().getCarDealFactoryProvider();
        this.wizardStepsProvider = ServiceLocator.getComposer().getCarDealWizardStepsProvider();
        this.validationMessagesPanel = new ValidationSummaryPanel();
        this.contentPresenter = new CardLayout();
        this.headerPanel = new CarDealWizardHeaderPanel("We will make you a great deal!");
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(this.contentPresenter);

        this.navigationPanel = new CarDealWizardNavigationPanel();
        this.navigationPanel.addListener(this);

        JPanel contentWithValidation = new JPanel();
        contentWithValidation.setLayout(new BorderLayout());
        contentWithValidation.add(this.contentPanel, BorderLayout.CENTER);
        contentWithValidation.add(this.validationMessagesPanel, BorderLayout.SOUTH);

        add(this.headerPanel, BorderLayout.NORTH);
        add(contentWithValidation, BorderLayout.CENTER);
        add(this.navigationPanel, BorderLayout.SOUTH);
    }

    @Override
    public void createItem() {
        log.info(() -> "Creating new CAR DEAL ...");
        CarDealFactory carDealFactory = this.carDealFactoryProvider.createItemFactory();
        wizard = new CarDealWizard(carDealFactory, this.wizardStepsProvider);
        wizard.addListener(this);
        this.initializeWizardStepsAndNavigateToFirstStep();
    }

    @Override
    public void editItem(int id) {
        log.info(() -> String.format("Editing CAR DEAL with id '%d' ...", id));
        CarDealDetails carDeal = this.carDetailsRepository.getDeal(id);
        CarDealFactory carDealFactory = this.carDealFactoryProvider.createItemFactory(carDeal);
        wizard = new CarDealWizard(carDealFactory, this.wizardStepsProvider);
        wizard.addListener(this);
        this.initializeWizardStepsAndNavigateToFirstStep();
    }

    private void initializeWizardStepsAndNavigateToFirstStep() {
        List<CarDealWizardStep> steps = this.wizardStepsProvider.getSteps();
        this.contentPanel.removeAll();
        steps.forEach(s -> {
            this.contentPanel.add(s, s.getStepKey());
        });

        this.contentPresenter.show(this.contentPanel, steps.get(0).getStepKey());
        boolean isLastStep = steps.size() == 1;
        boolean canGoForward = steps.size() > 1;

        this.headerPanel.setTitle(steps.get(0).getTitle(), 1, steps.size());
        this.navigationPanel.navigated(false, canGoForward, isLastStep);
    }

    @Override
    public void stepChanged(StepChangedEventArgs args) {
        this.navigationPanel.navigated(args.canGoBack(), args.canGoForward(), args.isLastStep());
        this.headerPanel.setTitle(args.getActiveStep().getTitle(), args.getCurrentStepNumber(), args.getTotalNumberOfSteps());
        this.validationMessagesPanel.clearValidationResults(args.getActiveStep().getFieldsMap());
        this.contentPresenter.show(this.contentPanel, args.getActiveStep().getStepKey());
    }

    @Override
    public void validationFailedOnNavigating(ValidationEventArgs args) {
        this.validationMessagesPanel.displayValidationResults(args.getValidationSummary(),
                args.getFieldsMap());
    }

    @Override
    public void completed(CarDealCompletedEventArgs args) {
        logSavingCarDeal(args.getCarDeal());
        CarDealDetails carDealToSave = args.getCarDeal();
        carDealToSave = this.carDetailsRepository.saveDeal(carDealToSave);
        BasicEventArgs dealArgs = new BasicEventArgs(this, carDealToSave.getId());
        logCarDealSaved(args.getCarDeal());
        this.listeners.notifyListeners(l -> l.itemSaved(dealArgs));
    }

    private void logSavingCarDeal(CarDealDetails carDeal) {
        log.info(() -> String.format("Saving CAR DEAL for '%s' who's made a deal to buy '%s' ...",
                carDeal.getCustomer().toString(),
                carDeal.getCar().toString()));
    }

    private void logCarDealSaved(CarDealDetails carDeal) {
        log.info(() -> String.format("CAR DEAL for '%s' who's made a deal to buy '%s' has been saved. Deal id is '%d'.",
                carDeal.getCustomer().toString(),
                carDeal.getCar().toString(),
                carDeal.getId()));
    }

    @Override
    public void navigateBack() {
        this.wizard.goBack();
    }

    @Override
    public void navigateForward() {
        tryNavigateForward();
    }

    @Override
    public void completeWizard() {
        tryNavigateForward();
    }

    private void tryNavigateForward() {
        try {
            this.wizard.goForward();
        } catch (ValidationException e) {
            log.log(Level.SEVERE, e, () -> "Failed to navigate to next step due to unresolved validation errors." +
                    " This suggests missing validation rules within one of the wizard steps!");
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validate failure",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            log.log(Level.SEVERE, e, () -> "Failed to navigate to next step due to critical system failure!");
        }
    }
}
