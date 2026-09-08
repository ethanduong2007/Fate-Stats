package com.example.application.views;

import com.vaadin.flow.component.menubar.MenuBar;

import com.vaadin.flow.component.contextmenu.MenuItem;

import com.vaadin.flow.component.contextmenu.SubMenu;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.List;

/**
 * @author Ethan Duong
 * @version 1.0
 */

@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private H1 viewTitle;

    public MainLayout() {
        addHeaderContent();
        setDrawerOpened(false);
        addClassName("main-layout-app-layout-1");
    }

    private void addHeaderContent() {
        viewTitle = new H1("Fate/Stats");
        viewTitle.getStyle().set("font-size", "30px").set("font-weight", "bold").set("margin", "0 0 0 25px");
        addToNavbar(true, viewTitle);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
