package Interface;

import SystemLogic.Admin;

public class AdminMenu extends Menu{

    @Override
    public void displayMenuOptions() {
        System.out.println("1. see objects of interest");
        System.out.println("2. see auctions");
        System.out.println("3. buy object");
        System.out.println("4. manage pending sign ups");
        System.out.println("5. create expert");
        System.out.println("0. logout");
    }

    @Override
    public int handleMenuInput(String input) {
        switch (input){
            case "1":
                super.seeObjects();
                return 0;
            case "2":
                super.seeAuctions();
                return 0;
            case "3":
                buyObject();
                return 0;
            case "4":
                managePendingSignUps();
                return 0;
            case "5":
                createExpert();
                return 0;
            case "0":
                super.logout();
                return 0;
            default:
                return 1;
        }
    }

    private void buyObject(){

    }

    private void managePendingSignUps(){
        ((Admin)InstitutionConsole.getCurrentUser()).approveSignUp();
    }

    private void createExpert(){

    }

}
