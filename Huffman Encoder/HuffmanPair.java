/**
 * HuffmanPair.java
 * Version 1
 * @author Andy Li
 * April 2020
 * Creates a comparable pair used for huffman encoding
 */
class HuffmanPair implements Comparable<HuffmanPair> {
    String element;
    int freq;
    public HuffmanPair(String element, int freq) {
        this.element = element;
        this.freq = freq;
    }
    public int compareTo(HuffmanPair pair) {
        return freq-pair.freq;
    }
    public String getElement() {
        return element;
    }
    public void setElement(String element) {
        this.element = element;
    }
    public int getFreq() {
        return freq;
    }
    public void setFreq(int freq) {
        this.freq = freq;
    }
    @Override
    public String toString(){
        return "("+element+","+freq+")";
    }
}