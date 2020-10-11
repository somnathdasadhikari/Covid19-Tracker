package com.example.covidtracker.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.covidtracker.domain.User;
import com.example.covidtracker.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@PageTitle("Login | Covid 19 Dashboard")
public class LoginView extends VerticalLayout {
	private TextField userNameField;
	private PasswordField passwordField;
	private UserService service;
	private BeanValidationBinder<User> binder;

	public LoginView(@Autowired UserService service) {
		
		this.service = service;
		
		H3 title = new H3("Covid-19 Dashboard");
		userNameField = new TextField("User name");
		passwordField = new PasswordField("Password");

		Span errorMessage = new Span();

		Button submitButton = new Button("Login");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		FormLayout formLayout = new FormLayout(title, userNameField, passwordField, errorMessage, submitButton);

		formLayout.setMaxWidth("500px");
		formLayout.getStyle().set("margin", "0 auto");

		formLayout.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("490px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));

		formLayout.setColspan(title, 2);
		formLayout.setColspan(errorMessage, 2);
		formLayout.setColspan(submitButton, 2);

		errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
		errorMessage.getStyle().set("padding", "15px 0");
		title.getStyle().set("margin-buttom", "20px");
		title.getStyle().set("text-align", "center");
		add(formLayout);
		
		binder = new BeanValidationBinder<User>(User.class);
		binder.forField(userNameField).asRequired().bind("username");
		binder.forField(passwordField).asRequired().bind("password");
		
		binder.setStatusLabel(errorMessage);
		
		submitButton.addClickListener(e -> {
			try {
				User detailsBean = new User();
				binder.writeBean(detailsBean);
				boolean validateLogin = service.validateLogin(detailsBean);
				if(validateLogin == true) {
					UI.getCurrent().navigate("dashboard");
					Notification notification = Notification.show("Welcome to our dashboard!");
					notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				}
				
				else {
					UI.getCurrent().navigate("login");
					Notification notification = Notification.show("Invalid User Name or Password");
					notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
				errorMessage.setText("User Name and Password can not be empty");
			}
		});
	}

}
