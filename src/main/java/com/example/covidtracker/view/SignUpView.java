package com.example.covidtracker.view;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.covidtracker.domain.User;
import com.example.covidtracker.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Signup | Covid 19 Dashboard")
public class SignUpView extends VerticalLayout {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private PasswordField passwordField1;
	private PasswordField passwordField2;
	private UserService service;
	private TextField emailField;
	private TextField firstnameField;
	private TextField lastnameField;
	private TextField userNameField;
	private BeanValidationBinder<User> binder;
	private boolean enableEmailValidation;
	private boolean enablePasswordValidation;

	public SignUpView(@Autowired UserService service) {

		this.service = service;

		H1 title = new H1("Covid-19 Dashboard");

		firstnameField = new TextField("First name");
		lastnameField = new TextField("Last name");

		userNameField = new TextField("User name");
		emailField = new TextField("Email Address");

		passwordField1 = new PasswordField("Wanted password");
		passwordField2 = new PasswordField("Password again");

		Span errorMessage = new Span();

		Button submitButton = new Button("Join the community");
		Button signinButton = new Button("Click here to Login");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		signinButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		signinButton.addClickListener(e -> UI.getCurrent().navigate("login"));

		FormLayout formLayout = new FormLayout(title, firstnameField, lastnameField, userNameField, emailField,
				passwordField1, passwordField2, errorMessage, submitButton, signinButton);

		formLayout.setMaxWidth("500px");
		formLayout.getStyle().set("margin", "0 auto");

		formLayout.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("490px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));

		formLayout.setColspan(title, 2);
		formLayout.setColspan(errorMessage, 2);
		formLayout.setColspan(submitButton, 2);
		formLayout.setColspan(signinButton, 2);

		errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
		errorMessage.getStyle().set("padding", "15px 0");
		signinButton.getStyle().set("margin-top", "20px");
		title.getStyle().set("margin-buttom", "20px");
		title.getStyle().set("text-align", "center");
		add(formLayout);

		binder = new BeanValidationBinder<User>(User.class);
		binder.forField(firstnameField).asRequired().bind("firstname");
		binder.forField(lastnameField).asRequired().bind("lastname");
		binder.forField(userNameField).asRequired().bind("username");
		binder.forField(emailField).withValidator(this::emailValidator).asRequired().bind("email");
		binder.forField(passwordField1).withValidator(this::passwordValidator).asRequired().bind("password");


		passwordField2.addValueChangeListener(e -> {
			enablePasswordValidation = true;
			binder.validate();
		});

		binder.setStatusLabel(errorMessage);

		submitButton.addClickListener(e -> {
			try {
				User detailsBean = new User();
				binder.writeBean(detailsBean);
				boolean validateRegistration = service.validateRegistration(detailsBean);
				if(validateRegistration == true) {
					service.store(detailsBean);
					UI.getCurrent().navigate("login");
					Notification notification = Notification.show("Welcome to our community, please login here!");
					notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				}
				
				else {
					UI.getCurrent().navigate("login");
					Notification notification = Notification.show("Your are arleady registered, please login here!");
					notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
				errorMessage.setText("Saving the data failed, please try again");
			}
		});
	}
	
	

	private ValidationResult emailValidator(String emailId, ValueContext ctx) {

		if (emailId == null) {
			return ValidationResult.error("Email field can not be empty");
		}
		
		if (!enableEmailValidation) {
			enableEmailValidation = true;
			return ValidationResult.ok();
		}
		
		String email = emailField.getValue();

		if (email != null && email.contains("@")) {
			return ValidationResult.ok();
		}

		if (email != null && email.contains(".")) {
			return ValidationResult.ok();
		}

		return ValidationResult.error("Email is not valid");
	}

	private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
		if (pass1 == null || pass1.length() < 8) {
			return ValidationResult.error("Password should be at least 8 characters long");
		}

		if (!enablePasswordValidation) {
			enablePasswordValidation = true;
			return ValidationResult.ok();
		}

		String pass2 = passwordField2.getValue();

		if (pass1 != null && pass1.equals(pass2)) {
			return ValidationResult.ok();
		}

		return ValidationResult.error("Passwords do not match");
	}

}
