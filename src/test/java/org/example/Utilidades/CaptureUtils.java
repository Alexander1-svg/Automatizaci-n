package org.example.Utilidades;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class CaptureUtils {

    public static void captureScreenShot(WebDriver webdriver, String filePath)throws IOException
    {
        //Convierte el webdriver en TakesScreenshot
        TakesScreenshot screenShot=((TakesScreenshot)webdriver);
        //Crear un archivo con la imagen
        File screenFile=screenShot.getScreenshotAs(OutputType.FILE);
        //Crea un archivo en la ruta destino
        File DestinoFile= new File(filePath);
        //Copia el archivo screenFile en DestinoFile
        FileUtils.copyFile(screenFile, DestinoFile);
    }

    //obtener la fecha actual
    public static String GetTimeStampValue() throws IOException
    {
        Calendar cal= Calendar.getInstance();
        Date time= cal.getTime();
        String timestamp= time.toString();
        //System.out.println( timestamp);
        String systime= timestamp.replace(":", "-");
        // System.out.println(systime);
        return systime;

    }
}
