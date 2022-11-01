import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Задача Написать программу, которая будет угадывать предмет, который загадал юзер.
        //Программа задает уточняющие вопросы, а юзер отвечает на них "да", либо "нет".
        //Например, предположим мы загадали слово "телефон".
        //
        //вопрос программы: Это живой предмет?
        //наш ответ: нет
        //вопрос программы: Это умеет гладить?
        //наш ответ: нет
        //вопрос программы: Это умеет звонить?
        //наш ответ: да
        //программа: Я угадала! Это телефон?
        //мы: да
        //программа: Ура! Сыграем еще?
        //
        //либо вторая концовка:
        //программа: Я угадала! Это домофон?
        //мы: нет
        //программа: Очень жаль. Тогда что это?
        //мы: телефон
        //программа: Расскажите про его свойства. Сколько свойств хотите указать?
        //мы: 4
        //программа: введите свойства:
        //неживой предмет
        //работает от зарядки
        //умеет звонить
        //умеет фотографировать
        //программа: Запомнила! Сыграем еще?
        //
        //либо третья концовка:
        //программа: Я не знаю, что это (
        //И дальше как во второй концовке: юзер вводит описание предмета
        //
        //То есть программа должна хранить список объектов и их описание. В процессе угадывания программа перебирает описания объектов и фильтрует неподходящие.
        //Также программа должна уметь пополнять свои знания.

        Subject sub1 = new Subject("apple");
        sub1.addKeyWord("неживой предмет");
        sub1.addKeyWord("съедобный предмет");
        sub1.addKeyWord("зеленый предмет");
        Subject sub2 = new Subject("телефон");
        sub2.addKeyWord("неживой предмет");
        sub2.addKeyWord("умеет звонить");
        sub2.addKeyWord("работает от зарядки");

        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(sub1);
        subjects.add(sub2);


        while(true){
            //копия списка предметов
            ArrayDeque<Subject> localList = new ArrayDeque(subjects);
            //список ключевых слов, ответ на который был "да"
            ArrayList<String> positiveAnswers = new ArrayList<>();
            ArrayList<String> negativeAnswers = new ArrayList<>();
            Scanner scn = new Scanner(System.in);
            System.out.println("Загадай предмет и нажми любую клавишу");
            scn.nextLine();
            String guess = "";
            //пока список предметов не пуст и пока программа не угадала предмет
            while(!localList.isEmpty() && !guess.equalsIgnoreCase("да")){
                //берем предмет из списка
                Subject s = localList.poll();
                //на случай, если перед ним юзер уже отвечал на вопросы
                //проверяем, что ключевые слова нового предмета соответствуют ключевым словам, ответ на который был "да"
                //если они не соответствуют, переходим к следующему предмету
                if(!matchAnswers(positiveAnswers, s)) continue;
                int match = 0;
                for (int i = 0; i < s.keywords.size(); i++) {
                    String keyword = s.keywords.get(i); //берем ключевое слово
                    //чтобы вопросы не повторялись, првоеряем, отвечал ли юзер на этот вопрос
                    //если да, засчитываем совпадение и переходим к следуюему вопросу
                    if(positiveAnswers.contains(keyword)) {
                        match++;
                        continue;
                    }
                    //если ответ есть в списке отрицательных, переходим к следующему предмету
                    if(negativeAnswers.contains(keyword)) break;
                    System.out.print("Это "+keyword+"? ");
                    String answer = scn.next();
                    //если юзер ответил "да", засчитываем совпадение и добавляем ключевое слово в список positiveAnswers
                    if(answer.equalsIgnoreCase("да")){
                        positiveAnswers.add(keyword);
                        match++;
                    }else{
                        //если ответил "нет", завершаем проверку предмета
                        negativeAnswers.add(keyword);
                        break;
                    }
                }
                //после каждой проверки предмета, проверяем кол-во совпадений
                //если оно равно кол-ву ключевых слов, предполагаем, что угадали
                if(match == s.keywords.size()){
                    System.out.print("Я угадал! Это "+s.name+"? ");
                    guess = scn.next();
                    if(guess.equalsIgnoreCase("да")){
                        System.out.println("Ура!");
                    }else{
                        //если нет, то продолжаем попытку
                        System.out.println("Жаль:( Попробую еще раз ");
                    }
                }
            }
            //если не угадали
            if(localList.isEmpty() && !guess.equalsIgnoreCase("да")){
                System.out.println("Не знаю, что это :( Как называется этот предмет? ");
                addNewSubject(subjects);
            }

        }
    }
    static void addNewSubject(ArrayList<Subject> subjects){
        Scanner scn = new Scanner(System.in);
        Subject subject = new Subject(scn.nextLine());
        System.out.print("Введите ключевые слова. Сколько слов будете вводить? ");
        int count = new Scanner(System.in).nextInt();
        for (int i = 1; i <= count; i++) {
            System.out.printf("Первое слово %d: ", i);
            subject.addKeyWord(scn.nextLine());
        }
        subjects.add(subject);
        System.out.println("Спасибо! Я запомнил :) Готов попробовать еще раз!");
    }
    static boolean matchAnswers(ArrayList<String> answers, Subject s){
        for (String answer : answers) {
            if(!s.keywords.contains(answer)) return false;
        }
        return true;
    }
}
