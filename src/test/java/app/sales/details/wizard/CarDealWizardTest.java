package app.sales.details.wizard;

import app.common.validation.ValidationEventArgs;
import com.sun.javaws.exceptions.InvalidArgumentException;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;
import core.validation.model.ValidationError;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import testing.helpers.TestData;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public final class CarDealWizardTest {

    @Test
    public final void GivenNewWizardThenActiveStepShouldBeStepOne() {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);
        // When
        // Then
        assertThat(sut.activeStep(), equalTo(step1));
    }

    @Test
    public final void GivenWizardAtTheFirstStepWhenStepIsValidAndWeGoForwardThenStepTwoShouldBeDisplayed() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        // When
        sut.goForward();

        // Then
        verify(step1, times(1)).validateStep();
        assertThat(sut.activeStep(), equalTo(step2));
    }

    @Test
    public final void GivenWizardWithTwoStepsWhenNavigatingForwardThenShouldRaiseFinishedEvent() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardEventListener eventListener = Mockito.mock(CarDealWizardEventListener.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());
        when(step2.getCarDeal()).thenReturn(factory);
        sut.addListener(eventListener);

        // When
        sut.goForward();
        sut.goForward();

        // Then
        verify(eventListener, times(1)).completed(any());
    }

    @Test
    public final void GivenWizardAtFinalStepWhenNavigatingForwardThenShouldReturnCarDeal()
            throws InvalidArgumentException, ValidationException {

        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealDetails carDeal = TestData.Deals.getDeal();
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardEventListener eventListener = Mockito.mock(CarDealWizardEventListener.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());
        when(step2.getCarDeal()).thenReturn(factory);
        when(factory.build()).thenReturn(carDeal);
        sut.addListener(eventListener);

        // When
        sut.goForward();
        sut.goForward();

        // Then
        ArgumentCaptor<CarDealCompletedEventArgs> argument = ArgumentCaptor.forClass(CarDealCompletedEventArgs.class);
        verify(eventListener).completed(argument.capture());
        assertThat(argument.getValue().getCarDeal(), is(carDeal));
    }

    @Test
    public final void GivenWizardNotAtTheFirstStepWhenMovingBackThenShouldGetToPreviousStep() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        // When
        sut.goForward();
        sut.goBack();

        // Then
        assertThat(sut.activeStep(), equalTo(step1));
    }

    @Test
    public final void GivenWizardNotAtTheFirstStepWhenMovingBackThenShouldNotPassPastFirstStep() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        // When
        sut.goForward();
        sut.goBack();
        sut.goBack();
        sut.goBack();

        // Then
        assertThat(sut.activeStep(), equalTo(step1));
    }

    @Test
    public final void GivenWizardWhenMovingForwardAndCurrentStepIsNotValidThenStayAtCurrentStep() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step3 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2, step3));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary(Arrays.asList(new ValidationError("field", "error"))));
        // When
        sut.goForward();
        sut.goForward();

        // Then
        assertThat(sut.activeStep(), equalTo(step2));
    }

    @Test
    public final void GivenWizardWhenMovingForwardAndCurrentStepIsNotValidThenRaiseValidationFailedEvent() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step3 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardEventListener eventListener = Mockito.mock(CarDealWizardEventListener.class);
        ValidationSummary failedValidationSummary =
                new ValidationSummary(Arrays.asList(new ValidationError("field", "error")));

        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2, step3));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(failedValidationSummary);
        sut.addListener(eventListener);

        // When
        sut.goForward();
        sut.goForward();

        // Then
        ArgumentCaptor<ValidationEventArgs> argument = ArgumentCaptor.forClass(ValidationEventArgs.class);
        verify(eventListener).validationFailedOnNavigating(argument.capture());
        assertThat(argument.getValue().getValidationSummary(), is(failedValidationSummary));
    }

    @Test
    public final void GivenWizardWhenMovingBackAndCurrentStepIsNotValidThenShouldStillMoveBack() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step3 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2, step3));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary(Arrays.asList(new ValidationError("field", "error"))));

        // When
        sut.goForward();
        sut.goBack();

        // Then
        assertThat(sut.activeStep(), equalTo(step1));
    }

    @Test
    public final void GivenWizardWhenCancelledThenGoToFirstStepAndClearAllSteps() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardEventListener eventListener = Mockito.mock(CarDealWizardEventListener.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        sut.addListener(eventListener);
        sut.goForward();

        // When
        sut.cancel();

        // Then
        assertThat(sut.activeStep(), equalTo(step1));
        verify(step1, times(1)).clear();
        verify(step2, times(1)).clear();
    }

    @Test
    public final void GivenWizardAtTheFirstStepWhenWeGoForwardThenStepChangedEventShouldBeRaised() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step3 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardEventListener eventListener = Mockito.mock(CarDealWizardEventListener.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2, step3));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());
        when(step3.validateStep()).thenReturn(new ValidationSummary());
        sut.addListener(eventListener);

        // When
        sut.goForward();

        // Then
        ArgumentCaptor<StepChangedEventArgs> argument = ArgumentCaptor.forClass(StepChangedEventArgs.class);
        verify(eventListener).stepChanged(argument.capture());
        assertThat(argument.getValue().getPreviousStep(), is(step1));
        assertThat(argument.getValue().getActiveStep(), is(step2));
        assertThat(argument.getValue().canGoBack(), equalTo(true));
        assertThat(argument.getValue().canGoForward(), equalTo(true));
    }

    @Test
    public final void GivenWizardWhenAtLastStepThenShouldNotBeAbleToNavigateForward() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step3 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardEventListener eventListener = Mockito.mock(CarDealWizardEventListener.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2, step3));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());
        when(step3.validateStep()).thenReturn(new ValidationSummary());
        sut.goForward();
        sut.addListener(eventListener);

        // When
        sut.goForward();

        // Then
        ArgumentCaptor<StepChangedEventArgs> argument = ArgumentCaptor.forClass(StepChangedEventArgs.class);
        verify(eventListener).stepChanged(argument.capture());
        assertThat(argument.getValue().getPreviousStep(), is(step2));
        assertThat(argument.getValue().getActiveStep(), is(step3));
        assertThat(argument.getValue().canGoBack(), equalTo(true));
        assertThat(argument.getValue().canGoForward(), equalTo(false));
    }

    @Test
    public final void GivenWizardWhenAtFirstStepThenShouldNotBeAbleToNavigateBack() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step3 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardEventListener eventListener = Mockito.mock(CarDealWizardEventListener.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2, step3));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());
        when(step3.validateStep()).thenReturn(new ValidationSummary());
        sut.goForward();
        sut.addListener(eventListener);

        // When
        sut.goBack();

        // Then
        ArgumentCaptor<StepChangedEventArgs> argument = ArgumentCaptor.forClass(StepChangedEventArgs.class);
        verify(eventListener, times(1)).stepChanged(argument.capture());
        assertThat(argument.getValue().getPreviousStep(), is(step2));
        assertThat(argument.getValue().getActiveStep(), is(step1));
        assertThat(argument.getValue().canGoBack(), equalTo(false));
        assertThat(argument.getValue().canGoForward(), equalTo(true));
    }

    @Test
    public final void GivenWizardWhenAtFirstStepThenFirstStepShouldReceiveDataContext() {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());

        // When
        // Then
        verify(step1, times(1)).setCarDeal(factory);
    }

    @Test
    public final void GivenWizardWhenMovingToNextStepThenDataContextShouldBePassedToThatStep() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealFactory factoryAfterStep1 = Mockito.mock(CarDealFactory.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);
        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));
        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);

        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());
        when(step1.getCarDeal()).thenReturn(factoryAfterStep1);

        // When
        sut.goForward();

        // Then
        verify(step1, times(1)).getCarDeal();
        verify(step2, times(1)).setCarDeal(factoryAfterStep1);
    }

    @Test
    public final void GivenWizardWhenMovingToPreviousStepThenDataContextShouldBePassedToThatStep() throws ValidationException {
        // Given
        CarDealFactory factory = Mockito.mock(CarDealFactory.class);
        CarDealWizardStepsProvider stepsProvider = Mockito.mock(CarDealWizardStepsProvider.class);
        CarDealFactory factoryAfterStep2 = Mockito.mock(CarDealFactory.class);
        CarDealWizardStep step1 = Mockito.mock(CarDealWizardStep.class);
        CarDealWizardStep step2 = Mockito.mock(CarDealWizardStep.class);

        when(stepsProvider.getSteps()).thenReturn(Arrays.asList(step1, step2));

        CarDealWizard sut = new CarDealWizard(factory, stepsProvider);


        when(step1.validateStep()).thenReturn(new ValidationSummary());
        when(step2.validateStep()).thenReturn(new ValidationSummary());
        when(step2.getCarDeal()).thenReturn(factoryAfterStep2);
        sut.goForward();

        // When
        sut.goBack();

        // Then
        verify(step2, times(1)).getCarDeal();
        verify(step1, times(1)).setCarDeal(factoryAfterStep2);
    }
}