package org.pjp.museum.ui.view.accessdenied;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Access Denied")
@Route(value = "accessdenied")
public class AccessDeniedView extends VerticalLayout {

	public static final String MESSAGE = "Please connect to the museum wi-fi network for access to the Museum App.";

	private static final long serialVersionUID = 9102902278596466238L;

    private final Image image = new Image("images/access_denied.jpg", "access_denied");

    private final Paragraph paragraph = new Paragraph(MESSAGE);

    public AccessDeniedView() {
        super();

        setSpacing(false);

        add(image, paragraph);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        getStyle().set("text-align", "center");
    }

}
