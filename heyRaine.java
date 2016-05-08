
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

import com.ibm.watson.developer_cloud.tone_analyzer.v3_beta.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3_beta.model.ToneAnalysis;

public class heyRaine {
	
	//Hashtable for storing the emotion values
	Hashtable <String, Double> emotion;
	
	public heyRaine()
	{
		//Initializing the table
		emotion = new Hashtable<String, Double>();
	}//end heyRaine
	
	public static void main(String[] args)
	{
		//Inputting the credentials for IBM Watson
		ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_02_11);
	    service.setUsernameAndPassword("994597b6-aacb-4b24-9235-4d8c1aa02a6a", "zXZydSF5i3VH");
	    
	    //Read user input
	    Scanner in = new Scanner(System.in);
	    System.out.print("Enter some text: ");
	    String text = in.nextLine();
	    
	    //Execute Tone Analysis
	    ToneAnalysis tone = service.getTone(text).execute();
	    String analyzedText = tone.toString();
	    heyRaine raine = new heyRaine();
	    raine.analyzeTone(analyzedText);
	}//end main
	
	public void analyzeTone(String text)
	{
	    String answer = text.replaceAll("\\s+", "");
	    answer = answer.replace("{", "");
	    answer = answer.replace("[", "");
	    answer = answer.replace("}", "");
	    answer = answer.replace("]", "");
	    String [] str = answer.split(",");
	    for(int i = 0; i < str.length; i++)
	    {
	    	//System.out.println(str[i]);
	    	if (str[i].contains("tone_name") && (i+1) < str.length)
	    	{
	    		double score = Double.parseDouble(str[i+1].split(":")[1]);
	    		//Had to remove quotes from the parsing to match hash table
	    		String emote = str[i].split(":")[1].replaceAll("\"", "");
	    		
	    		if(emotion.containsKey(emote))
	    		{
	    			emotion.put(emote, emotion.get(emote) + score);
	    		}
	    		else 
	    		{
	    			emotion.put(emote, score);
	    		}//end else
	    	}//end if
	    }//end for
	    
	    //Display Hash Table
	    Set<String> keys = emotion.keySet();
        for(String key: keys){
            System.out.println(key+" is: "+ emotion.get(key));
        }
	}//end analyzeTone
}//end class

