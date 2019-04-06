package ua.training;

import ua.training.game.GuessTheNumberController;
import ua.training.game.GuessTheNumberModel;
import ua.training.game.GuessTheNumberView;
import ua.training.game.utils.ResourceBundleManager;

public class Main {
    public static final int CONSOLE_SIZE = 250;
    public static void main(String [] args) {
        GuessTheNumberModel model = new GuessTheNumberModel();
        GuessTheNumberView view = new GuessTheNumberView(System.in, System.out, ResourceBundleManager.INSTANCE, CONSOLE_SIZE);
        GuessTheNumberController controller = new GuessTheNumberController(model, view);
        controller.execute();
    }
}
