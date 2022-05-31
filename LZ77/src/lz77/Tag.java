/**
 * 
 * This class describes  the tag concept 
 * 
 */
package lz77;

/**
 * 
 * @author Nada Omar Fathi 20190581
 * 
 */
public class Tag {
    
        public int  position;
        public int length;
        public char nextchar;
        
        public Tag()
        {
            this.position = 0;
            this.length = 0;
        }  
        
        
        
        /**
         * @param position :where the sequence starts
         * @param length   :how many char we will take
         * @param nextchar 
         */        
        public Tag(int position , int length , char nextchar)
        {
            this.position= position;
            this.length = length;
            this.nextchar = nextchar;
        }  
        
        
        
        /**
         * 
         * this function to display the values of the tag properties (position , length , next character)
         * 
         */        
        public void display()
        {
            System.out.println( "<"+ position + "," + length + ","  +  nextchar + ">") ;
        }
}