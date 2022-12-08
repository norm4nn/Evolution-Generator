package agh.ics.oop;

public class OptionsParser {

    final private String[] corrects =  {"f", "forward", "b", "backward", "l", "left", "r", "right"};


    private Boolean isCorrect(String arg) {
        for (String correct: this.corrects)
            if(arg.equals(correct)) return true;

        return false;
    }

    public MoveDirection[] parse(String[] args) throws IllegalArgumentException{
        int correctAmount = 0;
        int i = 0;

        for (String arg: args) {
            if (this.isCorrect(arg)) {
                correctAmount += 1;
                args[i] = arg.substring(0, 1);
            }
            i += 1;
        }


        MoveDirection[] result = new MoveDirection[correctAmount];
        i = 0;

        for (String arg: args)
                if (isCorrect(arg)) {
                    switch (arg) {
                        case "f" -> result[i] = MoveDirection.FORWARD;
                        case "b" -> result[i] = MoveDirection.BACKWARD;
                        case "l" -> result[i] = MoveDirection.LEFT;
                        case "r" -> result[i] = MoveDirection.RIGHT;
                    }
                    i += 1;
                }
                else {
                    throw new IllegalArgumentException(arg + " is not valid argument!!!");
                }



        return result;
    }
}
