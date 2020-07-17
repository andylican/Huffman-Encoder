/**
 * HuffmanEncode.java
 * Version 1
 * @author Andy Li
 * April 2020
 * Takes in a text file and huffman encodes it
 */

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
class HuffmanEncode {
    public static void main (String[] args) throws IOException{
         FileInputStream in = null;
          in = new FileInputStream("ORIGINAL.TXT");
          int c;
        SimpleMap<Byte,Integer>freq = new SimpleMap<Byte,Integer>();
        String msg = "";
        while ((c = in.read()) != -1) {
            msg += ((char)c + "");
           
        }
        in.close();
        //System.out.println(msg);
      
        for(int i=0; i<msg.length(); i++) {
            byte b = (byte)(msg.charAt(i));
            if(!freq.containsKey(b)) {
                freq.put(b,1);
            } else {
                int curFreq = freq.get(b);
                freq.put(b,curFreq+1);
            }
       }
        SimplePriorityQueue<HuffmanPair> pq = new SimplePriorityQueue<HuffmanPair>();
        SimpleMap<Byte,String>code = new SimpleMap<Byte,String>();
       //System.out.println(freq);
        for(int i=0; i<freq.size(); i++) {
            byte key = freq.getKey(i);
            int value = freq.get(key);
            pq.add(new HuffmanPair(key+"",value));
        }
        
        
        while(pq.size() > 1) {
            HuffmanPair left = pq.dequeue();
            HuffmanPair right = pq.dequeue();
            String punct = ")(";
            String newStr = "";
            if(punct.contains(left.getElement().charAt(left.getElement().length()-1)+"") && punct.contains(right.getElement().charAt(0)+"")) {
                newStr = "("+left.getElement()+right.getElement()+")";
            } else newStr = "("+left.getElement()+" "+right.getElement()+")";
            encode(left.getElement(),code,true);
            encode(right.getElement(),code,false);
            int newFreq= left.getFreq()+right.getFreq();
            pq.add(new HuffmanPair(newStr,newFreq));
        }
        FileOutputStream out = null;
        out = new FileOutputStream("COMPRESSED.MZIP");
        String name = "ORIGINAL.TXT";
        out.write(name.getBytes());
        out.write(13);
        String tree = pq.dequeue().getElement();
        out.write(tree.getBytes());
        out.write(13);
        DoublyLinkedList<Byte>bits = new DoublyLinkedList<Byte>();
        int bitIndex = 0;
        System.out.println("Finished encoding");
        System.out.println("");
        System.out.println("Encoded Values (ascii): ");
        System.out.println(code);
        File f = new File ("bits.txt");
       PrintWriter p = new PrintWriter(f);
        for(int i=0; i<msg.length(); i++) {
            String encoded = code.get((byte)(msg.charAt(i)));
            p.print(encoded);
            for(int j=0; j<encoded.length(); j++) {
                char ch = encoded.charAt(j);
                byte curBit = Byte.parseByte(ch+"");
                int mod = bitIndex % 8;
                int pos = bitIndex/8;
                byte curElement = 0;
                if(pos < bits.size()) {
                    curElement = bits.remove(pos);
                }
                
                curElement += (curBit << (7-mod));
                bits.add(pos,curElement);
                bitIndex++;
            }
            
        }
        p.close();
        int leftOver = 7 - ((bitIndex-1) % 8);
        
    
       out.write((byte)(leftOver+48));
       out.write(13);
      
        while(bits.size() > 0) {
            byte element = bits.remove(0); 
           out.write((element));
        }
       out.close(); 
    }
    public static void encode(String str,  SimpleMap<Byte,String>code,boolean onLeft) {
        String punct = "() ";
        int index = 0;
        //System.out.println(str);
        while(index < str.length()) {
            char c = str.charAt(index);
            int curIndex = index;
            if(!punct.contains(c+"")) {
                index++;
                while(index < str.length() && !punct.contains(str.charAt(index)+"")) {
                    index++;
                }
                byte b = Byte.parseByte(str.substring(curIndex,index));
                String digit = ""+0;
                if(!onLeft) digit = ""+1;
                if(!code.containsKey(b)) {
                    code.put(b,digit);
                } else {
                    String value = code.get(b);
                    code.put(b,digit+value);
                }
            }
            index++;
        }
    }
}
