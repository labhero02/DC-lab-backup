import java.util.*;
public class electionalgorithms {
    public static int ring(int start,int[] priority,int[] active){
        int n=priority.length;
        int curr = start;
        int next = (curr+1)%n;
        LinkedList<Integer> q = new LinkedList<>();
        LinkedList<Integer> id= new LinkedList<>();
        do{
            if (active[curr]==1 && active[next]==1){
                q.addLast(priority[curr]);
                id.addLast(curr);
                System.out.println("Message passed from "+curr+" to "+next);
                System.out.println("Queue : ");
                for(int i : q)
                {
                    System.out.print(i+" ");
                }
                System.out.println();
                curr=next;
                next=(next+1)%n;
            }
            else if(active[curr]==1 && active[next]==0) {
                System.out.println("Process " + curr + " tries to send to " + next);
                System.out.println("Process " + next + " failed to respond");
                next = (next + 1) % n;
            }
        }while(curr!=start || (curr ==start && id.isEmpty()));
        int maxp=-1,maxi=-1,i=0;
        for(Integer k : q){
            if (maxp<k){
                maxp=k;
                maxi=i;
            }
            i+=1;
        }
        return id.get(maxi);
    }
    public static int bully(int start,int[] priority, int[] active,int crash){
        Scanner sc = new Scanner(System.in);
        int n= priority.length;
        LinkedList<Integer> q= new LinkedList<>();
        q.addLast(start);
        while(!q.isEmpty()){
            int p = q.pop();
            for(int i=0;i<n;i++){
                if (priority[p]<priority[i]){
                    System.out.println("--> Message sent from "+ p+" to "+i);
                }
            }
            if(active[crash]==0){
                System.out.println("Do you want to recover the crashed process ?");
                String s1=sc.nextLine();
                if(s1.equals("yes"))
                active[crash]=1;
            }
            int no_active=0;
            for(int i=0;i<n;i++){
                if (priority[p]<priority[i] && active[i]==1){
                    no_active=1;
                    System.out.println("<-- Message OK from "+ i+" to "+p);
                    if(!q.contains(i)){
                        q.addLast(i);
                    }
                }
                else if (priority[p]<priority[i]){
                    System.out.println(">< No Reply from "+i+" to "+p);
                }
            }
            if(no_active==0){
                return p;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("No of processes : ");
        int n = sc.nextInt();
        int priority[] = new int[n];
        int active[] = new int[n];
        int co=-1,cp=-1;
        for(int i=0;i<n;i++){
            System.out.println("Priority of process "+i);
            priority[i] = sc.nextInt();
            System.out.println("Active of process "+i);
            active[i] = sc.nextInt();
            if (priority[i]>cp && active[i]==1) {
                co = i;
                cp = priority[i];
            }
        }
        System.out.println("Coordinator process is process "+ co + " with priority "+cp);
        System.out.println("Process to Crash");
        int crash = sc.nextInt();
        if (active[crash]==0){
            System.out.println("Already crashed");
        }
        else{
            active[crash]=0;
            System.out.println("Process "+crash+" crashed");
        }
        System.out.println("to start election");
        int start = sc.nextInt();
        if (active[start]==1){
            System.out.println("Process "+start+" call election");
            //co=bully(start,priority,active,crash);
            //cp=priority[co];
            co=ring(start,priority,active);
            cp=priority[co];
            System.out.println("Election conducted");
            System.out.println("Coordinator process is process "+ co + " with priority "+cp);
            for(int i=0;i<n;i++)
            {
                if(i!=co)
                System.out.println("Co ordinator msg from "+co+" to "+i);
            }
        }
        else{
            System.out.println("Initiator inactive");
        }
        sc.close();
    }
}
