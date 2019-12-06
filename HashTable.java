import static java.lang.Math.abs;
import java.util.ArrayList;

public class HashTable {
    private ArrayList<HashNode> bucket;
    private int buckets;
    private double LOAD_THRESHOLD;
    private int entries;

    public HashTable(){
        buckets = 10000;
        bucket = new ArrayList<HashNode>();
        LOAD_THRESHOLD = 0.5;
        entries = 0;

        for(int i = 0; i < buckets; i++){
            bucket.add(null);
        }
    }

    //hash function for key 
    int hashFunction(String key){
        return abs(key.hashCode() % buckets);
    }


    public boolean containsKey(String key){
        return bucket.get(hashFunction(key)) != null;
    }

    public String get(String key){
        HashNode head = bucket.get(hashFunction(key));

        while(head != null){
            if(head.key == key){
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    //Puts key,value in hashnode by hashing(key)
    public void put(String key, String value){
        int hashCode = hashFunction(key);
        HashNode head = bucket.get(hashCode);

        if(head == null){
            bucket.set(hashCode, new HashNode(key, value));
        }else{
            while(head != null){
                if(head.key == key){
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            HashNode node = new HashNode(key, value);
            node.next = bucket.get(hashCode);
            bucket.set(hashCode, node);
        }
        entries++;
        if((entries * 1.0 / buckets) >= LOAD_THRESHOLD){
            increaseBucket();
        }
    }

    //Remove function 
    public String remove(String key) throws Exception {
        int hashCode = hashFunction(key);
        HashNode head = bucket.get(hashCode);

        if(head != null){
            if(head.key == key){
                bucket.set(hashCode, head.next);
                entries--;
                return head.value;
            }else{
                HashNode prev = head;
                HashNode current = null;
                while(prev.next != null){
                    current = prev.next;
                    if(current.key == key){
                        prev.next = current.next;
                        entries--;
                        return current.value;
                    }
                }
                return head.value;
            }
        }else{
            throw new Exception();
        }
    }

    //Increases bucket size 
    public void increaseBucket() {
        ArrayList<HashNode> temp = bucket;
        buckets *= 2;
        bucket = new ArrayList<>(buckets);

        for(int i = 0; i < buckets; i++){
            bucket.add(null);
        }

        for(HashNode head : temp){
            while(head != null){
                put(head.key, head.value);
                head = head.next;
            }
        }

    }


}