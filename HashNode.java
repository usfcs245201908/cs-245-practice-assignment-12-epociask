//Hashnode class

public class HashNode{
       public String key;
        public String value;
        public HashNode next;

        public HashNode(String key, String value){
            this.key = key;
            this.value = value;
            next = null;
        }
}