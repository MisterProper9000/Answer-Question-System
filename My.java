import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
/**
 * Created by Даниил on 03.07.2016.
 */
/*
%%%%% Первичная задача:

Взять все слова в массив
Бегать по этому массиву и считывать всё.
Метрака: 1/|а-q|
Очки:
Если слово из ответа и вопроса рядом, то больше очков
Если в пределах плюс-минус 5 позиций встречается bad word, то очки пропорционально снимаются(та же метрика)
Улучшение -- если рядом с одним словом из ответа много слов вопроса, то коэффициент надо ввести.(скажем, 1 слово -- l=1, 2 слова -- k=1.5, 3 слова -- 2.)

пробегаем текст -- если ответ, тогда пробегаем текст и считаем очки.
Следующее улучшение: разбиенеие на предложения и накидывание коэффициентов в зависимости от количества предложений между словами.
Задача сейчас -- разобраться с метрикой и начислением баллов. Учитывать bad words только в пределах предложения слова.

%%%%% Основная задача:

Нейросеть решит, какой из вариантов ответа верный.
На вход нейрону будет подаваться 1/расстояние между словами.
Весы нейросети будут сгенерированы случайным образом
Дальше алгоритм обратной связи будет работать, пока программа не даст верного ответа

*/

public class My {


//метод обрабатывает исходный текст
public static ArrayList<String> TextAdaptation(int n) {
    ArrayList<String> Text = new ArrayList<String>();
    try {
        File MyFile = new File("C:\\Users\\Даниил\\IdeaProjects\\Answer_Question\\src\\Sourse.txt");
        FileReader fileReader = new FileReader(MyFile);

        BufferedReader reader = new BufferedReader(fileReader);

        String line;
        int text_number = -1;

        while ((line = reader.readLine()) != null) {
            if (line.equals("_#")){
                ++text_number;
            }
            else if (text_number == n) {
                do {
                    line = line.replaceAll("…", "").replaceAll("—", "").replaceAll(",", "").replaceAll("-", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\;", "").replaceAll("\\:", "").replaceAll("\\»", "").replaceAll("\\«", "").replaceAll("»", "").replaceAll("«", "");
                    String[] string = line.split(" ");

                    for (int y = 0; y < string.length; y++) {
                        //System.out.println(string[y]); Соотвествие распознанного слова его длине
                        if (string[y].length() > 1) {
                            char[] chars = string[y].toCharArray();

                            int ind = string[y].length() - 1;
                            //System.out.println(chars.length); Длина распознанного слова
                            if (chars[ind] == '.' || chars[ind] == '?' || chars[ind] == '!' || (chars[ind] == '.' && chars[ind - 1] == '.' && chars[ind - 2] == '.')) {
                                string[y] = string[y].replace(".", "").replace("?", "").replace("!", "").replace("...", "");
                                Text.add(string[y]);
                                Text.add(".");
                            }
                            else
                                Text.add(string[y]);
                        }

                    }

                } while (!((line = reader.readLine()).equals("_#")));
                break;
            }

            //if(text_number > n) break;
        }
        reader.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    //Отладка правильности распознования текста
    //for(int yo = 0; yo<Text.size(); yo++) System.out.println(Text.get(yo));
    return Text;
}

    static int isNEGATIVE = 0;



    public static ArrayList<String> QuestionAdaptation (int n) {
        ArrayList<String> Question = new ArrayList<String>();
        try {
            File MyFile = new File("C:\\Users\\Даниил\\IdeaProjects\\Answer_Question\\src\\Question.txt");
            FileReader fileReader = new FileReader(MyFile);

            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            int text_number = -1;
            line = reader.readLine();

            while ((line) != null) {
                if (line.equals("_#")){
                    ++text_number;

                }
                else if (text_number == n) {

                    //line = reader.readLine();
                    // System.out.println("сас");
                    //System.out.println(line);
                    line = line.replaceAll("\\?", "").replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\.", "").replaceAll("\\;", "").replaceAll("\\:", "").replaceAll("\\»", "").replaceAll("\\«", "");
                    String[] string = line.split(" ");
                    for (int y = 0; y < string.length; y++) {
                        if (!string[y].equalsIgnoreCase("куда") && !string[y].equalsIgnoreCase("какие") && !string[y].equalsIgnoreCase("каких") && !string[y].equalsIgnoreCase("какую") && !string[y].equalsIgnoreCase("какого") && !string[y].equalsIgnoreCase("какой") && !string[y].equalsIgnoreCase("в") && !string[y].equalsIgnoreCase("на") && !string[y].equalsIgnoreCase("за") && !string[y].equalsIgnoreCase("а") && !string[y].equalsIgnoreCase("о") && !string[y].equalsIgnoreCase("у") && !string[y].equalsIgnoreCase("к") && !string[y].equalsIgnoreCase("и") && !string[y].equalsIgnoreCase("с") && !string[y].equalsIgnoreCase("кем") && !string[y].equalsIgnoreCase("чем") && !string[y].equalsIgnoreCase("чём") && !string[y].equalsIgnoreCase("кого") && !string[y].equalsIgnoreCase("чего") && !string[y].equalsIgnoreCase("чему") && !string[y].equalsIgnoreCase("чему") && !string[y].equalsIgnoreCase("чей") && !string[y].equalsIgnoreCase("кто") && !string[y].equalsIgnoreCase("что") && !string[y].equalsIgnoreCase("ком")) {
                            if (string[y].equalsIgnoreCase("не") || string[y].equalsIgnoreCase("нет")) { //если в вопросе было отрицание, то тогда при выборе ответа будет выбираться не с максимальным баллом, а с минимальным
                                isNEGATIVE = 1;
                            }
                            Question.add(string[y]);
                        }

                    }
                    break;

                    //Оталдка правильности распознования вопроса
                    //for(int ys = 0; ys<Question.size(); ys++) System.out.println(Question.get(ys));
                }
                if(text_number > n) break;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Question;
    }


   static ArrayList<String> ANSWERS;     //Массив слов вариантов ответа.

    public static ArrayList<String> AnswerAdaptation (){
        ArrayList<String> Buffer = new ArrayList<String>();
        try {
            File MyFile = new File("C:\\Users\\Даниил\\IdeaProjects\\Answer_Question\\src\\Answer.txt");
            FileReader fileReader = new FileReader(MyFile);

            BufferedReader reader = new BufferedReader(fileReader);

            String line = reader.readLine();
            String[] string = line.split(" ");

            for(int i = 0; i < string.length; i++){
                Buffer.add(string[i]);
            }

            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Buffer;
    }















/*
        System.out.print("Варианты ответа: ");

        for (int u = 0; u<4; u++){
            String s = sc.next();
            Answer[u] = s;
            Points[u] = 0;
        }

        Bad_words[0]="не";
        Bad_words[1]="нет";

        for (int i = 0; i < 4; i++) {//Главный цикл для вариантов ответа

            for (int j = 0; j < Text.size(); j++){//ищем слова ответа
                if (Text.get(j).compareToIgnoreCase(Answer[i]) == 0){//если нашли слово ответа, то переходим к поиску слов вопроса или bad words и начислению очков в соответствии с точками.

                    int o = j;
                    int a = j;

                    //поиск Bad_words слева и справа от слова ответа в пределах того же предложения
                    while (!Text.get(o).equals(".") && o>=0){
                        if (Text.get(o).compareToIgnoreCase(Bad_words[0]) == 0 || Text.get(o).compareToIgnoreCase(Bad_words[1]) == 0) Points[i] = Points[i] - (double)8/Math.abs(j - o);
                        if (o>0) o = o-1;
                        else break;
                    }
                    o = j;
                    while (!Text.get(a).equals(".")){
                        if (Text.get(a).compareToIgnoreCase(Bad_words[0]) == 0 || Text.get(a).compareToIgnoreCase(Bad_words[1]) == 0) Points[i] = Points[i] - (double)8/Math.abs(j - a);
                        a = a+1;
                    }


                    a = j;

                    //Поиск слов вопроса слева и справа от слова ответа с учётом точек
                    while (o >= 0){
                        for(int qq = 0; qq < Question.size(); qq++) {
                            if (Text.get(o).compareToIgnoreCase(Question.get(qq)) == 0){
                                Points[i] = Points[i] + (double)k/Math.abs(j-o);
                                //Проверка на корректность обработки текста в поисках ответа на вопрос
                                //System.out.println("Я нашёлся с таким ответом к такому вопросу и вот мои балыы");
                                //System.out.println(Text.get(j));

                                //System.out.println(Answer[i]);
                                //System.out.println(Points[i]);
                            }
                            if (k != 1 && !Text.get(o).equals(".")) k = k - 2;
                        }
                        o = o-1;
                    }
                    k = 7;
                    while (a < Text.size()){
                        for(int qq = 0; qq < Question.size(); qq++) {
                            if (a != Text.size() && Text.get(a).compareToIgnoreCase(Question.get(qq)) == 0){
                                Points[i] = Points[i] + (double)k/Math.abs(j-a);
                                //Проверка на корректность обработки текста в поисках ответа на вопрос
                                //System.out.println("Я нашёлся с таким ответом к такому вопросу и вот мои балыы");
                                //System.out.println(Text.get(j));

                                //System.out.println(Answer[i]);
                                //System.out.println(Points[i]);
                            }
                            if (k != 1 && !Text.get(a).equals(".")) k = k - 2;
                        }
                        a = a+1;
                    }
                    k = 7;
                }
            }
        }

        System.out.print("Весы нейросети: ");

        double max = 0.0;
        e = 0;

        for (int i = 0; i<4; i++) {
            System.out.print(Points[i] + ", ");
            if (Points[i]>max){
                e=i;
                max=Points[i];
            }
        }

        System.out.println();
        System.out.print("Ответ: " + Answer[e]);

        System.out.println();


    }
    */

static final int INPUT_NEURONS = 10;
static final int HIDDEN_NEURONS_1 = 11;
static final int HIDDEN_NEURONS_2 = 5;
static final int OUTPUT_NEURONS = 4;
static final double LEARN_RATE = 0.001;


    public static double RAND_WEIGHT(){
        Random r = new Random();
        return  -0.5 + r.nextDouble();
    }

    public static double getSRand(){
        return Math.random();
    }
    public static double getRand(double x){
        return x * getSRand();
    }

    public static  double sqr(double x){
        return x * x;
    }


    //активаторы
    static double[] target = new double[OUTPUT_NEURONS];
    static double[] actual = new double[OUTPUT_NEURONS];
    static double[] inputs = new double[INPUT_NEURONS];
    static double[] hidden_1 = new double[HIDDEN_NEURONS_1];
    static double[] hidden_2 = new double[HIDDEN_NEURONS_2];

    //ошибки
    static double[] erro = new double[OUTPUT_NEURONS];
    static double[] errh_1 = new double[HIDDEN_NEURONS_1];
    static double[] errh_2 = new double[HIDDEN_NEURONS_2];

    //вход скрытых ячеек первого слоя + смещеие
    static double[][] wih_1 = new double[INPUT_NEURONS+1][HIDDEN_NEURONS_1];
    //вход во второй скрытый слой + смещение
    static double[][] wih_2 = new double[HIDDEN_NEURONS_1+1][HIDDEN_NEURONS_2];
    //вход выходных ячеек + смещение
    static double[][] who = new double[HIDDEN_NEURONS_2+1][OUTPUT_NEURONS];


    //функция сигмоид
    public static double sigmoid(double val){
        return (1.0 / (1.0 + Math.exp(-val)));
    }

    //нормализующая функция
    public static double sigmoidDerivative(double val) {
        return (val * (1.0 - val));
    }





//обучающий набор -- состоит из десяти чисел. Число наборов равно числу текстов на число вопросов на число ответов.
    //static final int ANSWER = 4; //Число ответов
    static final int TEXTS = 1; //Число текстов
    static final int QUESTION = 1; //Число вопросов по каждому тексту
    static double[][] Samples = new double[TEXTS*QUESTION*OUTPUT_NEURONS][INPUT_NEURONS+OUTPUT_NEURONS];  //число текстов умножено на число вопросов


//Список плохих слов
    static final String Bad_words[] = {"Нет", "Не"};


/*           ~~~~СОЗДАНИЕ ОБУЧАЮЩИХ НАБОРОВ~~~~              */

static int[][] TARGET = {{0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},

        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},

        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0},

        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0},

        {0, 0, 0, 1},
        {0, 0, 0, 1},
        {0, 0, 0, 1},
        {0, 0, 0, 1},

        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},

        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},

        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},

        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0},

        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 0, 0},

        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},

        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0},
        {1, 0, 0, 0}};


    public static double[][] AssignSamples(double[][] Samples){

        ANSWERS = AnswerAdaptation(); //заполнили массив вариантов ответа, чтобы потом со всем сравнивать.
        //int Nanswer = 0;

        for(int sample_counter = 0; sample_counter < TEXTS; sample_counter++) {       //заполняем один обучающий набор

            ArrayList<String> Text = new ArrayList<String>();  //для нового набора -- новый текст
            Text = TextAdaptation(sample_counter);
            for(int yo = 0; yo<Text.size(); yo++) System.out.print(Text.get(yo) + " ");
            System.out.println();

            for(int answer_counter=0; answer_counter<QUESTION; answer_counter++) { //на каждый текст три вопроса.

                ArrayList<String> Question = new ArrayList<String>();       //обрабатывается вопрос
                Question = QuestionAdaptation(sample_counter*QUESTION + answer_counter);
                for(int yo = 0; yo<Question.size(); yo++) System.out.print(Question.get(yo) + " ");
                System.out.println();

                for(int ans = 0; ans<OUTPUT_NEURONS; ans++) {

                    String Answer = ANSWERS.get((OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ans);
                    //После того, как считали текст, вопрос и слово ответа, заполняем ячейки обучающего набора (ТОЛЬКО ВХОДНЫЕ ДАННЫЕ. ПОСЛЕДНИЕ 4 ЯЧЕЙКИ ОТВЕЧАЮТ ЗА TARGET
                    System.out.println(Answer);
                    System.out.println();
                    //пробегаем по словам ответа и ищем слова вопроса

                    for (int word = 0; word < Text.size(); word++) {
                        if (Text.get(word).compareToIgnoreCase(Answer) == 0) {//если нашли слово ответа, то переходим к поиску слов вопроса или bad words и начислению очков в соответствии с точками.


                            int new_counter = word - 1; //новый счётчик для поиска слов вопроса. Слово ответа игнорируется
                            //int dot_counter = 0;
                            int input_index = 4;

                            while (input_index >= 0 && new_counter >= 0) {   //ищем слова вопроса слева от слова ответа в пределах четырёх предложений

                                if (input_index == 4 && (Text.get(new_counter).compareToIgnoreCase(Bad_words[0]) == 0 || Text.get(new_counter).compareToIgnoreCase(Bad_words[1]) == 0)) {
                                    Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ans][INPUT_NEURONS - 1] += (double)(1.0 / (Math.abs(word - new_counter)));     //Если в предложении, где нашлось слово ответа есть плохие слова, то они учитываются.
                                }

                                if (Text.get(new_counter).equals(".")) {    //При переходе к новому предложению расстояния записываются в другую ячейку входа
                                    input_index--;
                                } else {                                   //В пределах предложения ищем слова из вопроса
                                    for (int question_counter = 0; question_counter < Question.size(); question_counter++) {
                                        if (Text.get(new_counter).compareToIgnoreCase(Question.get(question_counter)) == 0) { //если нашлось слово вопроса, то ячейке приплюсовывается расстояние между словом вопроса и ответа
                                            //System.out.println("Я нашёл слово ответа: " + Question.get(question_counter) + " в " + sample_counter + "м тексте, рядом со вариантом ответа" + Answer + "на расстоянии " + 1.0 / (Math.abs(word - new_counter)));
                                            Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ans][input_index] += (double)(1.0 / (Math.abs(word - new_counter)));
                                        }
                                    }
                                }

                                new_counter--;
                            }

                            //теперь надо найти слова вопроса справа от слова ответа в пределах четырёх предложений
                            new_counter = word + 1; //счётчик указывает на следующее слово после слова ответа
                            input_index = 4;      //индекс снова указывает на ячейку входа, отвечающую за предложение в котором было найдено слово ответа.

                            while (input_index < 9 && new_counter < Text.size()) {

                                if (input_index == 4 && (Text.get(new_counter).compareToIgnoreCase(Bad_words[0]) == 0 || Text.get(new_counter).compareToIgnoreCase(Bad_words[1]) == 0)) {
                                    Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ans][INPUT_NEURONS - 1] += (double)(1.0 / (Math.abs(word - new_counter)));     //Если в предложении, где нашлось слово ответа есть плохие слова, то они учитываются.
                                }

                                if (Text.get(new_counter).equals(".")) {
                                    input_index++;
                                } else {
                                    for (int question_counter = 0; question_counter < Question.size(); question_counter++) {
                                        if (Text.get(new_counter).compareToIgnoreCase(Question.get(question_counter)) == 0) {
                                            //System.out.println("Я нашёл слово ответа: " + Question.get(question_counter) + " в " + sample_counter + "м тексте, рядом со вариантом ответа" + Answer + "на расстоянии " + 1.0 / (Math.abs(word - new_counter)));
                                            Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ans][input_index] += (double)(1.0 / (Math.abs(word - new_counter)));
                                        }
                                    }
                                }

                                new_counter++;
                            }
                        }
                    }



                }
            }
        }

        //Теперь заполнены все обучающие наборы. Заполнение той части массива Samples, которая отвечает за target:

        for(int i = 0; i < TEXTS; i++){
            for(int j = 0; j < QUESTION; j++){
                for(int k = 0; k < OUTPUT_NEURONS; k++){
                    int tmp = 0;
                    for(int sample_index = INPUT_NEURONS; sample_index<INPUT_NEURONS+OUTPUT_NEURONS; sample_index++){
                        Samples[(OUTPUT_NEURONS*QUESTION)*i + OUTPUT_NEURONS*j + k][sample_index] = TARGET[(OUTPUT_NEURONS*QUESTION)*i + OUTPUT_NEURONS*j + k][tmp];
                        tmp++;
                    }
                }
            }
        }
        return Samples;
    }


/*           ~~~~ПРОХОЖДЕНИЕ НЕЙРОСЕТИ~~~~              */
    public static int ITER;
    public static void FeedForward(){
        int inp, hid, out;
        double sum;

        //вычисление входов в скрытые слои
        for(hid = 0; hid < HIDDEN_NEURONS_1; hid++){
            sum = 0.0;
            for(inp =0; inp <INPUT_NEURONS; inp++){
                sum +=inputs[inp] * wih_1[inp][hid];
            }
            //добавляем смещение
            sum += wih_1[INPUT_NEURONS][hid];

            hidden_1[hid] = sigmoid(sum);
        }

        for(hid = 0; hid < HIDDEN_NEURONS_2; hid++){
            sum = 0.0;
            for(inp = 0; inp < HIDDEN_NEURONS_1; inp++){
                sum += hidden_1[inp] * wih_2[inp][hid];
            }
        }

        //вход в выходной слой
        for(out = 0; out < 1; out++) {
            sum = 0.0;
            for (hid = 0; hid < HIDDEN_NEURONS_2; hid++) {
                sum += hidden_2[hid] * who[hid][out];
            }

            //добавляем смещение
            sum += who[HIDDEN_NEURONS_2][out];

            actual[ITER] = sigmoid(sum);

        }
    }


/*           ~~~~ОБРАТНОЕ РАСПРОСТРАНЕНИЕ~~~~             */
    public static void BackPropogate(){
        int inp, hid, out;

        //вычисление ошибки выходного слоя
        for (out = 0; out < OUTPUT_NEURONS; out++) {
            erro[out] = (target[out] - actual[out]) * sigmoidDerivative(actual[out]);
        }

        //вычисление ошибки второго скрытого слоя
        for (hid = 0; hid < HIDDEN_NEURONS_2; hid++) {
            errh_2[hid] = 0.0;
            for (out = 0; out < OUTPUT_NEURONS; out++) {
                errh_2[hid] += erro[out] * who[hid][out];
            }

            errh_2[hid] *= sigmoidDerivative(hidden_1[hid]);
        }

        //вычисление ошибки первого скрытого слоя
        for (hid = 0; hid < HIDDEN_NEURONS_1; hid++) {
            errh_1[hid] = 0.0;
            for (out = 0; out < HIDDEN_NEURONS_2; out++) {
                errh_1[hid] += errh_2[out] * wih_2[hid][out];
            }

            errh_1[hid] *= sigmoidDerivative(hidden_1[hid]);
        }

        //обновление весов выходного слоя
        for (out = 0; out < OUTPUT_NEURONS; out++) {
            for (hid = 0; hid < HIDDEN_NEURONS_2; hid++) {
                who[hid][out] += (LEARN_RATE * erro[out] * hidden_2[hid]);
            }

            //обновление смещения
            who[HIDDEN_NEURONS_2][out] += (LEARN_RATE * erro[out]);
        }

        //обновление весов второго скрытого слоя
        for (hid = 0; hid < HIDDEN_NEURONS_2; hid++) {
            for (inp = 0; inp < INPUT_NEURONS; inp++) {
                wih_2[inp][hid] += (LEARN_RATE * errh_2[hid] * inputs[inp]);
            }

            //обновление смещения
            wih_2[INPUT_NEURONS][hid] += (LEARN_RATE * errh_2[hid]);
        }

        //обновление весов первого скрытого слоя
        for (hid = 0; hid < HIDDEN_NEURONS_1; hid++) {
            for (inp = 0; inp < INPUT_NEURONS; inp++) {
                wih_1[inp][hid] += (LEARN_RATE * errh_1[hid] * inputs[inp]);
            }

            //обновление смещения
            wih_1[INPUT_NEURONS][hid] += (LEARN_RATE * errh_1[hid]);
        }
    }

    static int action(double[] arr){
        int index, sel;
        double max;
        sel = 0;
        max = arr[sel];
        if (isNEGATIVE == 0){
            for (index = 1; index < OUTPUT_NEURONS; index++) {
                if (arr[index] > max) {
                    max = arr[index]; sel = index;
                }
            }
        }
        else
            for (index = 1; index < OUTPUT_NEURONS; index++) {
                if (arr[index] < max) {
                    max = arr[index]; sel = index;
                }
            }
        return(sel);
    }


    public static void  main(String[] args) {



        int inp, hid, out;
        for (inp = 0; inp < OUTPUT_NEURONS+1; inp++) {
            for (hid = 0; hid < HIDDEN_NEURONS_1; hid++) {
            wih_1[inp][hid] = RAND_WEIGHT();
            }
/*
            wih[0][hid] = 1;
            wih[1][hid] = 3;
            wih[2][hid] = 5;
            wih[3][hid] = 7;
            wih[4][hid] = 9;
            wih[5][hid] = 7;
            wih[6][hid] = 5;
            wih[7][hid] = 3;
            wih[8][hid] = 1;
            wih[9][hid] = -10;
                wih[10][hid] = 4;
*/
        }

        for (hid = 0; hid < HIDDEN_NEURONS_1 + 1; hid++) {
            for (out = 0; out < HIDDEN_NEURONS_2; out++) {
                wih_2[hid][out] = RAND_WEIGHT();
            }
        }

        for (hid = 0; hid < HIDDEN_NEURONS_2 + 1; hid++) {
            for (out = 0; out < 1; out++) {
                who[hid][out] = RAND_WEIGHT();
            }
        }





        Scanner sc = new Scanner(System.in);
        Samples = AssignSamples(Samples);
        System.out.println("Я создал семпл");
        double err;
        int i, iterations = 0;
        int sum = 0;
        DataOutputStream DataOut;

        try {
            DataOut = new
                    DataOutputStream(new FileOutputStream("C:\\Users\\Даниил\\IdeaProjects\\Answer_Question\\src\\Errors.txt"));
        }catch(IOException exc){
            System.out.println("Cannot open output file");
            return;
        }

        try {

            while (true) {

                for(int sample_counter = 0; sample_counter < TEXTS; sample_counter++) {
                    for(int answer_counter=0; answer_counter<QUESTION; answer_counter++) {
                        for(ITER = 0; ITER<OUTPUT_NEURONS; ITER++) {

                            for (int in = 0; in < INPUT_NEURONS; in++) {
                                inputs[in] = Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ITER][in];
                            }

                            for (int tar = INPUT_NEURONS; tar < INPUT_NEURONS + OUTPUT_NEURONS; tar++) {
                                target[(tar - 2) % 4] = Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ITER][tar];
                            }

                            FeedForward();
                        }


                        err = 0.0;
                        for (i = INPUT_NEURONS; i < INPUT_NEURONS + OUTPUT_NEURONS; i++) {
                            err += sqr((Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ITER-1][i]) - actual[(i - 2) % 4]);
                            System.out.format("mse = %f%n", err);
                        }
                        err = 0.5 * err;

                        //System.out.format("mse = %f%n", err);
                        DataOut.writeDouble(err);
                        DataOut.writeBytes("\r\n");

                        BackPropogate();
                    }
                }

                if (iterations++ > 25000) break; //|| err < 0.110141




                //ITER = 0;


            }

        }catch(IOException exc) {
            System.out.println("Write Error.");
        }

        try {
            DataOut.close();
        }catch(IOException exc) {
            System.out.println("Error closing output file.");
            //return;
        }

        /* Проверить сеть */

        try {
            DataOut = new
                    DataOutputStream(new FileOutputStream("C:\\Users\\Даниил\\IdeaProjects\\Answer_Question\\src\\Weights.txt"));
        }catch(IOException exc){
            System.out.println("Cannot open output file");
            //return;
        }

        try {
            for(int sample_counter = 0; sample_counter < TEXTS; sample_counter++) {
                for (int answer_counter = 0; answer_counter < QUESTION; answer_counter++) {
                    for (ITER = 0; ITER < OUTPUT_NEURONS; ITER++) {


                        for (int in = 0; in < INPUT_NEURONS; in++) {
                            inputs[in] = Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ITER][in];
                        }

                        for (int tar = INPUT_NEURONS; tar < INPUT_NEURONS + OUTPUT_NEURONS; tar++) {
                            target[(tar - 2) % 4] = Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ITER][tar];
                        }
                        FeedForward();

                    }
                    //ITER = 0;
                    if (action(actual) != action(target)) {
                        DataOut.writeDouble(inputs[0]);
                        DataOut.writeDouble(inputs[1]);
                        DataOut.writeDouble(inputs[2]);
                        DataOut.writeDouble(inputs[3]);
                        DataOut.writeBytes(ANSWERS.get(action(actual)));
                        DataOut.writeBytes(ANSWERS.get(action(target)));
                        //System.out.println("Я ошибся в ответе на " + check + "ый вопрос");
                        for (int o = 0; o < 4; o++) {
                            System.out.println(target[o] + " " + actual[o]);
                        }
                        System.out.println();

                        DataOut.writeBytes("\r\n");
                    } else {
                        //System.out.println(action(actual));
                        sum++;
                    }
                }
            }



            DataOut.writeBytes("Network is ");
            DataOut.writeDouble(((float) sum / (float) (TEXTS * QUESTION * OUTPUT_NEURONS)) * 100.0);
            DataOut.writeBytes("% correct");
            System.out.println("Network is " + ((float) sum / (float) (TEXTS * QUESTION)) * 100.0 + "% correct");

        }catch(IOException exc) {
            System.out.println("Write Error.");
        }

        try {
            DataOut.close();
        }catch(IOException exc) {
            System.out.println("Error closing output file.");
            //return;
        }

        for(int sample_counter = 0; sample_counter < TEXTS; sample_counter++) {
            for(int answer_counter=0; answer_counter<QUESTION; answer_counter++) {
                for (ITER = 0; ITER < OUTPUT_NEURONS; ITER++) {
                    for (int j = 0; j < INPUT_NEURONS + OUTPUT_NEURONS; j++) {
                        System.out.print(Samples[(OUTPUT_NEURONS*QUESTION)*sample_counter + OUTPUT_NEURONS*answer_counter + ITER][j] + " ");
                    }

                    System.out.println();
                }
            }
        }
        /*
        System.out.println("Весы входного слоя");
        for (int ie = 0; ie<INPUT_NEURONS+1; ie++){
            for(int j = 0; j<HIDDEN_NEURONS; j++){
                System.out.print(wih[ie][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Весы выходного слоя");
        for (int ie = 0; ie<HIDDEN_NEURONS; ie++){
            for(int j = 0; j<1; j++){
                System.out.print(wih[ie][j] + " ");
            }
            System.out.println();
        }
*/
    }
}
