import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //kullanıcıdan girdileri almak içim bir scanner sınıfı oluşturuyoruz.
        Scanner scanner = new Scanner(System.in);

        //Kullanıcıdan tek kişilik mi yoksa iki kişilik mi oynadığını sorar ve ona göre kod satırları çalışır.
        System.out.println("1 or 2 players?");
        String players = scanner.nextLine();

        String word;

        //Eğer tek oyunculu modu seçerse daha önceden bir diziye atamış olduğumu kelimler ile oyunu oynamaya başlıyor.
        if (players.equals("1")) {
            word = getRandomWord();
        } else {
            //İki oyunculu modu seçtiğinde ise ilk oyuncudan kelime alıyoruz.
            System.out.println("Player 1, please enter your word:");
            word = scanner.nextLine();

            //Birinci oyuncu kelimeyi yazdığı taktirde ikinci oyuncunun bu kelimeyi görmemesi için alt alta boşluk bıraktım.
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            clearConsole();
            System.out.println("Ready for player 2! Good luck!");
        }

        // Oyuncunun tahminlerini ve yanlış sayısını takip etmek için listeler oluşturuyoruz.
        List<Character> playerGuesses = new ArrayList<>();
        Integer wrongCount = 0;

        while (true) {
            //asma figürünü ekrana yazdırıyoruz.
            printHangedMan(wrongCount);
            if (wrongCount >= 6) {
                //yanlış sayısı 6'dan fazla ise oyunu kaybediyoruz ve kaybettin yazısı ile beraber kelimenin ne olduğu ekranda yazıyor.
                System.out.println("You lose. -_-");
                System.out.println("The word : " + word);
                break;
            }

            printWordState(word, playerGuesses);
            //Verilen kelimeyi bulmak için önümüzde iki yol var:
            //İlk yol harf tahmini yapmak ikinci yol ise kelimeyi tahmin etmek.
            //Aşağıda buna karar veriliyor.
            System.out.println("Guess a character (C) or the word (W):");

            //klavyeden girilen küçük harfleri büyük harfe çeviriyor. Bu sayede kullanıcı küçük harf dahi girse kod çalışmış oluyor.
            String choice = scanner.nextLine().toUpperCase();

            if (!choice.equals("C") && !choice.equals("W")) {
                System.out.println("Invalid choice! Please enter 'C' for letter guess or 'W' for word guess.");
                continue;
                // Hata durumunda döngünün başına geri dön.
            }

            if (choice.equals("C")) {
                //Oyuncu ekrana 'C' yazarsa harf tahmin ediyor.
                if (!getPlayerGuess(scanner, word, playerGuesses)) {
                    //Tahmin ettiği harf yanlış ise 'yanlış sayacı' bir artıyor.
                    wrongCount++;

                }
            } else if (choice.equals("W")) {
                // Oyuncu ekrana 'W' harfini ekrana yazarsa kelime tahmin ediyor.
                System.out.println("Please enter your guess for the word:");
                //Şayet kelimeyi doğru bulursa kazanaıyor.
                if (scanner.nextLine().equalsIgnoreCase(word)) {
                    System.out.println("You win! = *_*");
                    //Kelimeyi bulduğu taktirde kod kırılıyor ve çalışmayı durduruyor.
                    break;
                } else {
                    System.out.println("Nope! Try again.");
                }
            }
        }
    }

    //Şayet tahmin ettiğimiz harfler yanlış ise burada adamımızın çizimlerini ekrana yazdırıyoruz.
    private static void printHangedMan(Integer wrongCount) {
        System.out.println(" -------");
        System.out.println(" |     |");
        if (wrongCount >= 1) {
            System.out.println(" O");
        }

        if (wrongCount >= 2) {
            System.out.print("\\ ");
            if (wrongCount >= 3) {
                System.out.println("/");
            } else {
                System.out.println("");
            }
        }

        if (wrongCount >= 4) {
            System.out.println(" |");
        }

        if (wrongCount >= 5) {
            System.out.print("/ ");
            if (wrongCount >= 6) {
                System.out.println("\\");
            } else {
                System.out.println("");
            }
        }
        System.out.println("\n\n");
    }

    private static boolean getPlayerGuess(Scanner keyboard, String word, List<Character> playerGuesses) {
        System.out.println("Please enter a character:");
        String guess = keyboard.nextLine().toLowerCase();
        if (guess.length() == 1) {
            char guessedChar = Character.toUpperCase(guess.charAt(0)); // Kullanıcının girdiği harfi büyük harfe dönüştürüyoruz.
            playerGuesses.add(guessedChar);
            return word.toUpperCase().contains(Character.toString(guessedChar));
        }
        return false;
    }

    private static boolean printWordState(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));
                correctCount++;
            } else {
                System.out.print("-");
            }
        }
        System.out.println();

        return (word.length() == correctCount);
    }

    public static String getRandomWord(){
        Random random = new Random();
        int index = random.nextInt(WordsClass.words.length);
        return WordsClass.words[index];
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    //Bu Java kodu, konsol ekranını temizlemek için kullanılır. Kodun adı "clearConsole" ve içindeki işlev şudur:
    //    \033[H ve \033[2J karakter dizileri ANSI kaçış dizileridir. Bunlar, ANSI standartlarını kullanarak konsol ekranını temizlemek için kullanılır.
    //    \033[H karakter dizisi, konsolun sol üst köşesine (\033) imleci (H) yerleştirir.
    //    \033[2J karakter dizisi, tüm konsol ekranını temizler.
    //Bu sayede kod çalıştırıldığında konsol ekranı temizlenir ve kullanıcıya yeni bir boş ekran sunulur.
}
