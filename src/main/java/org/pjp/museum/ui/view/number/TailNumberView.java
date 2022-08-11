package org.pjp.museum.ui.view.number;

import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.service.bean.TailNumber;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Enter Tail Number")
@Route(value = "tail", layout = MainLayout.class)
public class TailNumberView extends VerticalLayout implements AfterNavigationObserver, ValueChangeListener<ComponentValueChangeEvent<ListBox<TailNumber>, TailNumber>> {

    private static final long serialVersionUID = 2220031256723181930L;

    private final ListBox<TailNumber> listBox = new ListBox<>();

    private final ExhibitService service;

    public TailNumberView(ExhibitService service) {
        super();
        this.service = service;

        listBox.addValueChangeListener(this);

        listBox.setHeightFull();

        setHorizontalComponentAlignment(Alignment.CENTER, listBox);

        add(listBox);

        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        listBox.setItems(service.getTailNumbers());
    }

    @Override
    public void valueChanged(ComponentValueChangeEvent<ListBox<TailNumber>, TailNumber> event) {
        UI.getCurrent().navigate(ExhibitView.class, event.getValue().uuid());
    }

}
