import java.awt.geom.AffineTransform;
import java.io.File;
import java.nio.channels.Pipe.SinkChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class RandomizedBST implements TaxEvasionInterface{
    Random random = new Random();
    public RandomizedBST(Comparator<LargeDepositor> comparator) {
        this.comparator = comparator;
    }

    private class ListNode <T>{
        T item ;
        ListNode node ;
        ListNode nextListNode;
        public ListNode(T item){
            this.item = item ; 
            this.nextListNode = null;
        }
        
        
    }
    public class List<T>{
        ListNode root ; 
        public List(){
            this.root = null ; 
        }
        
        public void add(T item){
            if (this.root == null) { 
                this.root = new ListNode(item); 
            }else {
                ListNode next = root;
                while (next.nextListNode !=null) {
                    next = next.nextListNode;
                }
                next.nextListNode = new ListNode<T>(item);
            }
        }

        public boolean isEmpty(){
            if(root == null){
                return true; 
            }
            return false;
        }

        public void printList(){
            ListNode next = root ; 
            while(next!=null){
                System.out.println(next.item);
                next = next.nextListNode;
            }
        }
    }


    private class LastNameComparator implements Comparator<LargeDepositor> {
        @Override
        public int compare(LargeDepositor o1, LargeDepositor o2) {
            return o1.getLastName().compareTo(o2.getLastName());
        }
    }
    private class AFMComparator implements Comparator<LargeDepositor> {
        @Override
        public int compare(LargeDepositor o1, LargeDepositor o2) {
            return Integer.compare(o1.getAFM(), o2.getAFM());
        }
    }
    private class TreeNode{
        private LargeDepositor item;
        private TreeNode left;
        private TreeNode right;
        int N;

        public TreeNode (LargeDepositor item){
            this.item = item;

        }

        public LargeDepositor getData() {
            return item;
        }

        public void setData(LargeDepositor item) {
            this.item = item;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }

    private TreeNode root;
    private Comparator<LargeDepositor> comparator;
    private LastNameComparator LastNameComparator;


    private TreeNode insertRoot(LargeDepositor item , TreeNode n ){
        if(n == null){
            return new TreeNode(item);
        }
        if (Math.random()*(n.N+1) < 1.0){
            return insertT(n,item);
        }
        if(item.key()< n.item.key()){
            n.left = insertRoot(item, n.left);
        }
        else if (item.key() > n.item.key()){
            n.right = insertRoot(item, n.right);
           
        }
        n.N ++;
        return n;
    }
    private TreeNode insertT(TreeNode h, LargeDepositor item ) {
        if (h == null)
            return new TreeNode(item);

        if (item.key() < h.item.key()) {
            h.left = insertT(h.left, item);
            h = rotateRight(h); 
        } else {
            h.right = insertT(h.right,item);
            h = rotateLeft(h); 
        }

        h.N++;
        return h;
    }


    private TreeNode rotateRight(TreeNode x) {
        TreeNode y = x.left;
        x.left = y.right;
        y.right = x;
        updateSize(x); 
        return y;
    }

    private TreeNode rotateLeft(TreeNode y) {
        TreeNode x = y.right;
        y.right = x.left;
        x.left = y;
        updateSize(y); 
        return x;
    }

    private void updateSize(TreeNode node) {
        node.N = size(node.left) + size(node.right) ;
    }

    private int size(TreeNode node) {
        return node != null ? node.N : 0;
    }
    
    @Override
    public void insert(LargeDepositor item){
        this.root = insertRoot(item, root);
    }

    

    @Override
    public void load(String filename) {
        int AFM;
        String firstName;
        String lastName;
        double savings;
        double taxedIncome;
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filename);
            return;
        }
        try {
            Scanner readFile = new Scanner(file);
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                String[] splitText = line.trim().split("\\s+");
                if (splitText.length < 5) {
                    System.out.println("Skipping line due to insufficient data: " + line);
                    continue;
                }
                AFM = Integer.parseInt(splitText[0]);
                firstName = splitText[1];
                lastName = splitText[2];
                savings = Double.parseDouble(splitText[3]);
                taxedIncome = Double.parseDouble(splitText[4]);
                LargeDepositor LargeDepositor = new LargeDepositor(AFM, firstName, lastName, savings, taxedIncome);
                insert(LargeDepositor);
            }
            System.out.println("Finished reading file: " + filename);
        } catch (Exception ex) {
            System.out.println("An error occurred while reading the file: " + filename);
            ex.printStackTrace();
        }
    }
    @Override
    public void updateSavings(int AFM, double savings) {
       LargeDepositor searchingdDepositor = searchByAFM(AFM);
        if ( searchingdDepositor== null){
            System.out.println("Depositor didnt found");
            return;
        }

        searchingdDepositor.setSavings(savings);
    }

    @Override
    public LargeDepositor searchByAFM(int AFM) {
    Comparator<LargeDepositor> comparator = new AFMComparator();
    TreeNode current = root;
        while (current != null) {
            LargeDepositor currentData = current.getData();
            int comparison = comparator.compare(currentData, new LargeDepositor(AFM, "", "", 0.0, 0.0));
            if (comparison == 0) {
                return currentData;
            } else if (comparison < 0) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }
        System.out.println("The item does not exist");
        return null;
    }
    @Override
    public List<LargeDepositor> searchByLastName(String lastName) {
        Comparator<LargeDepositor> lastNameComparator = new LastNameComparator();
        List<LargeDepositor> depositors = new List<>();//Change implementation with my own 
        searchByLastNameHelper(root, lastName, depositors);
        if (depositors.isEmpty()) {
            System.out.println("The item does not exist");
        }
        return depositors;
    }

    private void searchByLastNameHelper(TreeNode node, String lastName, List<LargeDepositor> depositors) {
        if (node == null) {
            return;
        }
        LastNameComparator lastNameComparator = new LastNameComparator();
        int comparison = lastNameComparator.compare(node.getData(), new LargeDepositor(0, "", lastName, 0.0, 0.0));
        if (comparison == 0) {
            depositors.add(node.getData());
        }
        searchByLastNameHelper(node.getLeft(), lastName, depositors);
        searchByLastNameHelper(node.getRight(), lastName, depositors);
    }
    @Override
    public void remove(int AFM) {
        TreeNode current = root;
        TreeNode parent = null;
        TreeNode replace = null;

        while(true){
            if(current == null){
                return;
            }
            if(current.getData().getAFM() == AFM){
                break;
            }
            parent = current;

            if(current.getData().getAFM() < AFM){
                current  = current.getRight();
            }
            else{
                current = current.getLeft();
            }
        }

        if(current.getLeft() == null){
            replace = current.getRight();
        }
        else if(current.getRight() == null){
            replace = current.getLeft();
        }
        else{
            TreeNode findCurrent = current.getRight();

            while (true){
                if(findCurrent.getLeft() != null){
                    findCurrent = findCurrent.getLeft();
                }
                else {
                    break;
                }
            }
            remove(findCurrent.getData().getAFM());

            findCurrent.setLeft(current.getLeft());
            findCurrent.setRight(current.getRight());
        }

        if(parent == null){
            root = replace;
        }else{
            if(parent.getLeft() == current){
                parent.setLeft(replace);
            }
            if(parent.getRight() == current){
                parent.setRight(replace);
            }
        }

    }

    private int countAllSuspects(TreeNode t){
        if (t == null){
            return 0;
        }
        return 1 + countAllSuspects(t.left) + countAllSuspects(t.right);
    }

    private double countSAvingsFromAllSuspects(TreeNode t){
        if (t == null){
            return 0;
        }
        return t.getData().getSavings() + countSAvingsFromAllSuspects(t.left) + countSAvingsFromAllSuspects(t.right);
    }
    @Override
    public double getMeanSavings() {
        return countAllSuspects(root) / countSAvingsFromAllSuspects(root);
    }

    
 @Override 
public void printTopLargeDepositors(int k) {
    java.util.List<LargeDepositor> topDepositors = new ArrayList<>();
    getTopDepositors(root, topDepositors, k);
    for (LargeDepositor depositor : topDepositors) {
        System.out.println(depositor);
    }
}

private void getTopDepositors(TreeNode node, java.util.List<LargeDepositor> topDepositors, int k) {
    if (node == null || topDepositors.size() >= k) {
        return;
    }
    getTopDepositors(node.getRight(), topDepositors, k);
    if (topDepositors.size() < k) {
        topDepositors.add(node.getData());
        getTopDepositors(node.getLeft(), topDepositors, k);
    }
}
    @Override
    public void printByAFM() {
        printAfm(root);
    }

    private void printAfm(TreeNode root) {
        if(root == null){
            return;
        }
        printAfm(root.getLeft());
        System.out.println(root.getData().toString());
        printAfm(root.getRight());
    }

    static void printMenu(){
        System.out.println("1--> Insert Depositor");
        System.out.println("2--> Load Depositors from a file");
        System.out.println("3--> Update savings from a Depositor");
        System.out.println("4--> Search a Depositor by AFM");
        System.out.println("5--> Search a Depositor by last name");
        System.out.println("6--> Remove a Depositor");
        System.out.println("7--> Get mean savings");
        System.out.println("8--> Print top Depositors");
        System.out.println("9--> Print Depositors by AFM");
        System.out.println("0-->1 Exit");
    }
    public static void main(String[] args) {
        RandomizedBST myTree = new RandomizedBST(new LargeDepositorComparator());
        Scanner scanner = new Scanner(System.in);
        boolean running=true;
        while (running) {
            printMenu();
        int input = scanner.nextInt();
        if (input == 1){ 
            System.out.println("AFM Depositor");
            int afm = scanner.nextInt();
            System.out.println("Name of Depositor");
            String name = scanner.next();
            System.out.println("Last name Depositor");
            String lastName = scanner.next();
            System.out.println("Savings Depositor");
            double savings = scanner.nextDouble();
            System.out.println("Taxed incomed Depositor");
            double taxedincome = scanner.nextDouble();
            myTree.insert(new LargeDepositor(afm, name, lastName, savings, taxedincome));
        }
        else if( input == 2){
            System.out.println("Insert file path");
            myTree.load(scanner.next());
        }
        else if( input == 3){
            System.out.println("Type AFM and savings to update");
            int afm = scanner.nextInt();
            double savings = scanner.nextDouble();
            myTree.updateSavings(afm, savings);
        }
        else if( input == 4){
            System.out.println("Type Depositor's AFM");
            LargeDepositor result =myTree.searchByAFM(scanner.nextInt());
            System.out.println(result);
        }
        else if( input == 5){
            System.out.println("Type Depositor's last name");
            String last_name = scanner.next();
            List<LargeDepositor> results =myTree.searchByLastName(last_name);
            results.printList();
        }
        else if( input == 6){
            System.out.println("Type the AFM of the Depositor you want to remove");
            myTree.remove(scanner.nextInt());
        }
        else if( input == 7){
            System.out.println(myTree.getMeanSavings());
        }
        else if( input == 8){
            System.out.println("How many of top Depositor's you want to print:");
            myTree.printTopLargeDepositors(scanner.nextInt());
        }
        else if( input == 9){
            myTree.printByAFM();
        }
        else if( input == 0){
            running = false;
        }
        else{
            System.out.println("Invalid choice. Please try again.");
        }
        }
        scanner.close();
    }

    
}
