import java.io.*;

public class Record  {
    public int createRecord(int points){

        String strRes;
        int res = 0;

        BufferedReader br = null;
        try{

            File file = new File("src/resources/Record.txt");
            //Проверка на наличие файла
            if(!file.exists()){
                file.createNewFile();
            }

            //Чтение из файла
            br = new BufferedReader(new FileReader("src/resources/Record.txt"));
            strRes = br.readLine();//запись написанной строки

            //Преобразование строки в целочисленный тип данных
            try{res = Integer.parseInt(strRes.trim());}
            catch (NumberFormatException nfe){System.out.println("NumberFormatException: " + nfe.getMessage());}

            if(points > res){
                //Запись в файл
                PrintWriter pw = new PrintWriter(file);
                pw.print(points);
                pw.close();
            }

        }catch(IOException e){
            System.out.println("Error"+e);
        }finally {
            try {
                br.close();//Закрытие чтения
            } catch (IOException e) {
                System.out.println("Error"+e);
            }
        }

        return res;
    }


}
