package com.company.menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    protected ArrayList<MenuChoice> currentMenu;

   // public ArrayList<MenuChoice> setInitialMenu();

    protected void setMenu(Object o) {
        currentMenu = (ArrayList<MenuChoice>) o;
    } // setMenu

    private void printMenu() {
        System.out.println("");
        for (MenuChoice m : currentMenu) {
            System.out.printf("%s%n", m.getFullTitle());
        } // for m...
        System.out.print("Ange ditt val: ");
    } // printMenu

    // get users choice
    private MenuChoice getMenuChoice() {
        String sChoice;
        Scanner scan = new Scanner(System.in);

        // check that there is some choice and not an empty input
        do
            sChoice = scan.nextLine();
        while (sChoice.length() == 0);

        for (MenuChoice m : currentMenu) {
            if (m.getKey() == sChoice.charAt(0))
                return m;
        } // for...
        return null;
    } // getMenuChoice

    public void handleMenu() {
       // currentMenu = setInitialMenu();

        MenuChoice m;
        boolean bStop = false;
        while (!bStop) {
            printMenu();

            m = getMenuChoice();
            if (m == null)
                System.out.println("Wrong choice, try again!");
            else {
                System.out.printf("Your choice is: %s%n", m.getTitle());
                bStop = m.getFunctionToCall() == null;
                if (!bStop) {
                    m.getFunctionToCall().accept(m.getParameter());
                } // if bStop...
            } // else
        } // while
    } // handleMenu

}
