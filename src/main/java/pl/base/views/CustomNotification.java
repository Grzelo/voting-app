package pl.base.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * The CustomNotification class provides methods for creating different types of notifications.
 */
public class CustomNotification {

    /**
     * Creates a success notification with the given text.
     *
     * @param text the text to display in the notification
     */
    public static void createSuccessNotification(String text) {
        Notification notification = new Notification();
        notification.setDuration(3000);
        notification.setPosition(Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        Icon icon = VaadinIcon.CHECK_CIRCLE.create();
        Div info = new Div(new Text(text));

        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    /**
     * Creates an error notification with the given text.
     *
     * @param text the text to display in the notification
     */
    public static void createErrorNotification(String text) {
        Notification notification = new Notification();
        notification.setDuration(3000);
        notification.setPosition(Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Div info = new Div(new Text(text));

        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    /**
     * Creates an information notification with the given text.
     *
     * @param text the text to display in the notification
     */
    public static void createInformationNotification(String text) {
        Notification notification = new Notification();
        notification.setDuration(3000);
        notification.setPosition(Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        Icon icon = VaadinIcon.INFO.create();
        Div info = new Div(new Text(text));

        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
}
