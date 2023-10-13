package org.pjp.museum.ui.view.accessdenied;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Access Denied")
@Route(value = "accessdenied")
public class AccessDeniedView extends VerticalLayout implements HasUrlParameter<Boolean>, AfterNavigationObserver {

	public static final String MESSAGE = "Please connect to the museum wi-fi network for access to the Museum App.";

	private static final long serialVersionUID = 9102902278596466238L;

    private final Image image = new Image("images/access_denied.jpg", "access_denied");

    private final Paragraph paragraph = new Paragraph(MESSAGE);

    private Boolean hideMessage;
    
    public AccessDeniedView() {
        super();

        setSpacing(false);

        add(image, paragraph);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        getStyle().set("text-align", "center");
    }

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Boolean parameter) {
		this.hideMessage = parameter;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		if (Boolean.TRUE.equals(hideMessage)) {
			paragraph.setText(null);
		}
	}

}
