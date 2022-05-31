/**
 * 
 * This class to run the whole program and contains every functions I need 
 * 
 */
package lz77;

/**
 * I used the array list data structure and some of its functions(add(object) - get(index) - size ())
 * 
 */
import java.util.ArrayList;

/**
 *
 * @author Nada Omar Fathi 
 * 
 */
public class LZ77 {
    
    // This list to group all tages in one place so we can display them and make decompress on them 
    public static ArrayList <Tag> list = new ArrayList <>();   
    
    
    /**
     * 
     * This function to display all the Tages in the list
     * 
     */
    public static void displayTages()
    {
        for (int i = 0; i<list.size(); i++)
        {
            list.get(i).display();    
        }  
    }
    
    
    
    /**
     * This function is to search about one char in the list which i created to hold the encoded characters 
     * 
     * @param c 
     * @param searchbuffer : the list where I put the encoded characters 
     * @return integer value which represent index of the character in list 
     * 
     */
    public static int searchAboutChar(char c, ArrayList<Character> searchbuffer) {
        int lastIndex = -1;
        for(int i = 0 ; i < searchbuffer.size(); i++)
        {
            if(c == searchbuffer.get(i)){
                lastIndex = i;
            }
        }
        return lastIndex;        
    }
    
    
    /**
     * This function is to search about string in the main string 
     * @param searchstring : the string which we want to find it 
     * @param searchbuffer : the list where the encoded characters exits 
     * @return integer value which represent  the starting index of that string if it in the main string 
     * 
     */
    
   public static int searchAboutString(String searchstring, ArrayList<Character> searchbuffer ) {
       
        String string = ""; 
        
        int num ;
        
        for (int i = 0 ; i < searchbuffer.size(); i++){
            string += searchbuffer.get(i);
        }
        
        boolean found =  string.contains(searchstring); 
        
        if(found == true){
            num = string.lastIndexOf(searchstring);
        }
        else
            num = -1;
        
        return num;       
    }
    
    
    /**
     * this function used to compress some text using lz77 technique and result in list of tags 
     * @param mainstring : array of characters which we want to compress
     */
    
    public static void compress(char [] mainstring)
    {
        
        int i = 0, mainlength = mainstring.length;
        
        // This list to hold the encoded chararcters 
        ArrayList <Character> searchbuffer = new ArrayList <>();
        
        
        while(i < mainlength)
        { 
            if (i == 0){                
                Tag tagobject = new Tag(0,0,mainstring[0]);
                list.add(tagobject);
                searchbuffer.add(mainstring[0]); 
            }            
            
            else{ 
                int len = 0; 
            
                Tag tagobject = new Tag();
                
                int result = searchAboutChar(mainstring[i] ,searchbuffer ) ;
                
                // the case that if the char not found in the encoded list
                if(result == -1){ 
                    tagobject.nextchar = mainstring[i];
                    searchbuffer.add(mainstring[i]); 
                    list.add(tagobject);
                } 
                else 
                {                       
                    int thePosition = -1; 
                    
                    int result2 = -1;
                    
                    // increase the len bacause we have now one char
                    len ++;
                    
                    String string = "";                    
                    
                    string += mainstring[i];
                    
                    int j = i+1;                    
                    
                    while(j < mainstring.length)
                    {                         
                        string += mainstring[j];
                        
                        result2 = searchAboutString(string ,searchbuffer);  
                        
                        if(result2 != -1){
                            len++;
                            // put the value of result2 in the position to be used later ----> the start index of the string in mainstring
                            thePosition = result2;
                        }
                        else{ 
                            string = "";
                            break;
                        }                        
                        j++;
                    }  
                    
                    tagobject.length = len; 
                    
                    if(thePosition == -1 )
                        tagobject.position = i - result;
                    else
                        tagobject.position = i - thePosition ; 
                     
                     
                    int index = i + len ;

                    if(index >= mainstring.length)
                    {
                        // '\0' : null char 
                        tagobject.nextchar = '\0';                        
                    }
                    else{
                        tagobject.nextchar = mainstring[index];
                    }
                    
                    for (int k = 0; k <= len ; k++)
                    {
                        if( i + k >= mainstring.length)
                            break;
                        else{
                            searchbuffer.add(mainstring[i+k]);
                        }                        
                    }
                        
                    i += len;
                    
                    list.add(tagobject);                    
                }
            }
           i++; 
        }
    }
    
    
    /**
     * The decompress fun which take list of tags and results in the real string 
     * @param list : this list contains all tags we want to decompress
     */
    public static void decompress (ArrayList <Tag> list)
    {
        // This string to hold the final result 
        String string = "";
                
        for(int i = 0; i < list.size();i++)
        {
            if(list.get(i).position == 0 && list.get(i).length == 0){
                string += list.get(i).nextchar;
            }
            else{  
                /*
                    ABAA ---> the third tag is  <2,1,A> 
                    so i want to go back 2 steps and then take one element and then take the next char
                    to go 2 stpes: len of the string - position     2 - 2 = 0
                    to take the elements do the next loop         result of the first part in loop  is A   + nextxhar in the part2 in the loop A  = AA
                */
                
                int index = string.length() - list.get(i).position;
                
                for(int j = 0 ; j < list.get(i).length; j++){  
                    
                    char c = string.charAt(index+j);
                    string += c;
                    
                    if( j == list.get(i).length-1) {
                        if(list.get(i).nextchar == '\0')
                            break;
                         else{
                            string += list.get(i).nextchar;
                        }
                    }                    
                }
            }
        }
        System.out.println("The result of the decompression : " + string);
    }
    

    /**
     * @param args 
     */
    public static void main(String[] args){
        // ABAABABAABBBBBBBBBBBBA   ABAABABAABBBBBBBBBBB    ABAABAB  
        String mainstring = "ABAABAB";
        
        char [] somecharacters = mainstring.toCharArray();
        
        compress(somecharacters);
        
        System.out.println("The main string is : " + mainstring);
        
        displayTages();
        
        decompress(list);
        
    }    
}