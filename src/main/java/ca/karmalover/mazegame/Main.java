package ca.karmalover.mazegame;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Random random = new Random();
        int x = random.nextInt(20) + 1;
        // Start junction
        new Junction("Main", null,
                // A
                new Junction("Forward", null, "You are safe... For now.",
                        // Question 1
                        new Junction("Forward", new Puzzle("Solve for x 7x - 8 = 9x + 32\n(only answer with a number)", "-20"), "Dead end ha!")),
                // Junction B
                new Junction("Right", null, "I mean there might be something here we will never know ;D",
                        // Question 2
                        new Junction("Forward", new Puzzle("What year did java first appear?", "1995"), "Nice you are making progress",
                                // Junction D
                                new Junction("Forward", null, "Getting a little warm >:)",
                                        // Junction E
                                        new Junction("Forward", null, "Getting a little hot over hear",
                                                // Junction F
                                                new Junction("Left", null, "mirror mirror who is the biggest of them al- Sike this isn't a question haha!",
                                                        // Junction Fake End
                                                        new Junction("Forward", null, "The ships hung in much the same way that bricks dont.",
                                                                new Junction("End", null, "Welcome to the end are you a good player! (I didnt add and end please terminate the program.")),
                                                        // Question 5
                                                        new Junction("Right", new Puzzle("Is python a real programming language", "no"), "Good! We have established a while ago that python doesnt count.",
                                                                // Junction G
                                                                new Junction("Down", null, "The average population of the universe is 0",
                                                                        // Junction H
                                                                        new Junction("Down", null, "Roses are red, Bow down to your master, The children are fast, But Elmo is faster",
                                                                                // Question 4
                                                                                new Junction("Down", new Puzzle("What is the First Rank as a NCM in the Royal Canadian Navy (Sort Form)", "S3"), "hahahah, you get a nother dead end >:D"))))))),
                                // Junction C
                                new Junction("Right", null, "Maybe this one?",
                                        // Question 3
                                        new Junction("Down", new Puzzle("What is (9x) + 32 if x was " + x, String.valueOf(9*x+32)), "Dead end my friend D:"))))
                ).print();
    }

    public static void prompt(String prompt) {
        System.out.print(prompt + " > ");
    }

    public static class Junction {
        public final List<Junction> paths = new ArrayList<>();
        public Junction previous;
        public final String name;
        public final Puzzle puzzle;

        public String message;

        public Junction(String name, Puzzle puzzle, Junction... junctions) {
            this.puzzle = puzzle;
            this.name = name;

            for (Junction junction : junctions) {
                this.paths.add(junction);
                junction.previous = this;
            }

            this.message = "Welcome to " + name + "!";
        }

        public Junction(String name, Puzzle puzzle, String message, Junction... junctions) {
            this(name, puzzle, junctions);

            this.message = message;
        }

        public boolean enter() {
            if (puzzle != null) {
                prompt(puzzle.prompt);

                String answer = scanner.nextLine();

                return answer.equalsIgnoreCase(puzzle.answer);
            }

            return true;
        }

        public void print() {
            System.out.println(message);
            if(previous != null) System.out.println("0: Go back");
            for (int i = 0; i < paths.size(); i++) {
                Junction path = paths.get(i);
                System.out.println(i + 1 + ": " + path.name);
            }

            prompt("Pick an option");

            try {
                int result = Integer.parseInt(scanner.nextLine());

                if(result > paths.size()) throw new Exception();
                if(result < 0) throw new Exception();

                if(result == 0 && previous == null) throw new Exception();

                if(result == 0) {
                    if(previous.enter()) {
                        previous.print();
                        return;
                    } else throw new IncorrectAnswer();
                }
                Junction path = paths.get(result - 1);
                if(path.enter()) {
                    path.print();
                } else throw new IncorrectAnswer();
            } catch(IncorrectAnswer incorrectAnswer) {
                System.out.println("That's the wrong answer. You have been returned to your previous junction.");

                if(previous != null) {
                    previous.print();
                    return;
                }
                print();
            } catch(InputMismatchException exception) {
                System.out.println("Improper input. Try again.");

                scanner.next();

                print();
            } catch (Exception exception) {
                System.out.println("Improper input. Try again.");

                print();
            }
        }
    }

    public static class Puzzle {
        public final String prompt;
        public final String answer;

        public Puzzle(String prompt, String answer) {
            this.prompt = prompt;
            this.answer = answer;
        }
    }

    public static class IncorrectAnswer extends Exception { }
}