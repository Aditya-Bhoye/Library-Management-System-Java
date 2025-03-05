import java.io.*;
import java.util.*;
import java.util.Random;
class Book implements Serializable{
    private static final long serialVersionUID = 1L;
    long book_id;
    String book_name;
    String author_name;
    float price;
    int quantity;
    public Book(long book_id,String book_name,String author_name,float price,int quantity){
        this.book_id=book_id;
        this.book_name=book_name;
        this.author_name=author_name;
        this.price=price;
        this.quantity=quantity;
    }
    public void display(String book_name,String author_name,long book_id,double price,int quantity){

        System.out.printf("%-20s | %-20s | %-15d | %-10.4f | %-10d",book_name.toUpperCase(),author_name.toUpperCase(),book_id,price,quantity);
        System.out.println(" ");
    }
    public void display_all(){
        System.out.printf("%-20s | %-20s | %-15d | %-10.4f | %-10d",book_name.toUpperCase(),author_name.toUpperCase(),book_id,price,quantity);
    }
}
class User implements Serializable{
    private static final long serialVersionUID = 1L;
    String name;
    int user_id;
    long password;
    String role;
    public User(String name,int user_id,long password,String role) {
        this.name = name;
        this.user_id = user_id;
        this.password = password;
        this.role = role;
    }
    public boolean login(int id,long password){
        boolean isLogin=false;
        if((id==1) && password==123456789){
            isLogin=true;
        }
        return isLogin;
    }
}
class IssueBook implements Serializable{
    private static final long serialVersionUID = 1L;
    long book_id;
    int user_id;
    public IssueBook(long book_id,int user_id){
        this.book_id=book_id;
        this.user_id=user_id;
    }
}
class SerializationUtils {
    public static <T> void saveData(ArrayList<T> dataList, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(dataList);
            System.out.println("Data saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving data to " + filename + ": " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found for " + filename + ", starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from " + filename + ": " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
public class Main {
    public static void main(String[] args) {

        String bookFile = "books.ser";
        String userFile = "users.ser";
        String issueFile = "issued_books.ser";
        Scanner a= new Scanner(System.in);
        Random r=new Random();
        ArrayList<Book> book= SerializationUtils.loadData(bookFile);
        ArrayList<User> user=new ArrayList<>();
        user.add(new User("admin",1,123456789,"admin"));
        SerializationUtils.saveData(user, userFile);
        ArrayList<IssueBook> issuebook=new ArrayList<>();
        System.out.println("---------------------------------- Welcome to Aditya's Library -------------------------------------");
        while(true){
            try {
                System.out.println("------------------------------------------------------------------------------------------------");
                System.out.println(" ");

                boolean found6=false;
                System.out.println("1.Manage Books\n2.Register User\n3.Issue Book\n4.Return Book\n5.Show All Books\n6.Exit the System");
                int choice = a.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("1.Add Book\n2.Delete Book\n3.Search Book\n4.Update Stock\n");
                        int choice1 = a.nextInt();
                        switch (choice1) {
                            case 1:
                                System.out.println("Enter Book Name ");
                                a.nextLine();
                                String book_name = a.nextLine();
                                System.out.println("Enter Author Name ");
                                String author_name = a.nextLine();
                                for (Book books : book) {
                                    if ((books.book_name.contains(book_name)) && (books.author_name.contains(author_name))) {
                                        System.out.println("Book is already exited in System");
                                        found6=true;
                                        break;
                                    }
                                }
                                if(!found6){
                                    long book_id = 100000000 + r.nextInt(900000000);
                                    System.out.println("Book ID for \"" + book_name + "\" is " + book_id);
                                    System.out.println("Enter price of the book ");
                                    float price = a.nextFloat();
                                    System.out.println("Enter how many quantity to add ");
                                    int quantity = a.nextInt();
                                    book.add(new Book(book_id, book_name, author_name, price, quantity));
                                    System.out.println("\"" + book_name + "\" is added successfully ");
                                    SerializationUtils.saveData(book, bookFile);
                                    break;
                                }
                                break;
                            case 2:
                                System.out.println("Enter Book ID to delete from the System");
                                long book_id1 = a.nextLong();
                                boolean found = false;
                                for (int i = 0; i < book.size(); i++) {
                                    if (book.get(i).book_id == book_id1) {
                                        found = true;
                                        System.out.println("\"" + book_id1 + "\" is removed from the System ");
                                        book.remove(i);
                                        SerializationUtils.saveData(book, bookFile);
                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("Book with " + book_id1 + " is not found in the System ");
                                    break;
                                }
                                break;
                            case 3:
                                boolean found3 = false;
                                System.out.println("Enter Book ID/Name/Author name to search in the System ");
                                a.nextLine();
                                String search = a.nextLine();
                                try {
                                    long number = Long.parseLong(search);
                                    for (int i = 0; i < book.size(); i++) {
                                        if (book.get(i).book_id == number) {
                                            found3 = true;
                                            System.out.println("Book is found");
                                            System.out.printf("%-20s | %-20s | %-15s | %-10s | %-10s","Book Name","Author Name","Book Id","Book Price","Quantity\n");
                                            System.out.println("---------------------------------------------------------------------------------");
                                            book.get(i).display(book.get(i).book_name, book.get(i).author_name, book.get(i).book_id, book.get(i).price, book.get(i).quantity);
                                            System.out.println("---------------------------------------------------------------------------------");
                                        }
                                    }

                                } catch (NumberFormatException e) {
                                    for (Book books : book) {
                                        if ((books.book_name.contains(search)) || (books.author_name.contains(search))) {
                                            found3 = true;
                                            System.out.println("Book is found");
                                            System.out.printf("%-20s | %-20s | %-15s | %-10s | %-10s","Book Name","Author Name","Book Id","Book Price","Quantity\n");
                                            System.out.println("---------------------------------------------------------------------------------");
                                            books.display(books.book_name, books.author_name, books.book_id, books.price, books.quantity);
                                            System.out.println("---------------------------------------------------------------------------------");
                                        }
                                    }
                                }
                                if (!found3) {
                                    System.out.println("Book is not found ");

                                }
                                break;
                            case 4:
                                boolean found1=false;
                                System.out.println("Enter Book Id to update stock ");
                                long book_id2=a.nextLong();
                                for(Book books:book){
                                    if(books.book_id==book_id2){
                                        found1=true;
                                        System.out.println("Now current stock is "+books.quantity);
                                        System.out.printf("%-10s | %10s","1.Increase Stock","2.Decrease stock ");

                                        int c=a.nextInt();
                                        System.out.println("Current stock of Book "+books.quantity);
                                        System.out.println("---------------------------------------------------------------------------------");
                                        switch(c){
                                            case 1:
                                                System.out.println("Enter number to increase in stocks ");

                                                int n=a.nextInt();
                                                books.quantity+=n;
                                                SerializationUtils.saveData(book, bookFile);
                                                break;
                                            case 2:
                                                if(books.quantity==0){
                                                    System.out.println("Books Quantity is already Zero \n You cant decrease it any more ");
                                                }
                                                else{
                                                    System.out.println("Enter number to decrease in stocks ");

                                                    int n1=a.nextInt();
                                                    if(n1<0){
                                                        System.out.println("The stock should not be in negative ");

                                                        break;
                                                    }
                                                    books.quantity-=n1;
                                                    System.out.println("Now current stock is "+books.quantity);
                                                    SerializationUtils.saveData(book, bookFile);
                                                }
                                                break;
                                            default:
                                                System.out.println("Error \"Might be wrong input\"");

                                                break;
                                        }
                                    }
                                    else{
                                        System.out.println("Book is not found ");
                                        break;
                                    }
                                }
                                break;
                            default:
                                System.out.println("Error \"Might be wrong input\"");
                                break;
                        }
                        break;
                    case 2:
                        System.out.printf("%-10s | %-10s","1.Add a User","2.Add a Staff");
                        System.out.println(" ");
                        int choice3=a.nextInt();
                        switch(choice3){
                            case 1:
                                System.out.println("Enter Your name");
                                a.nextLine();
                                String name=a.nextLine();
                                System.out.println("Enter Your Age ");
                                int age=a.nextInt();
                                if(age<6){
                                    System.out.println("The Age of User must be above 6 years ");
                                    break;
                                }
                                int user_id=100+r.nextInt(900);
                                System.out.println("Your User ID is "+user_id);
                                System.out.println("Set password for Your Account ");
                                long password=a.nextLong();
                                long length =(long) (Math.log10(password)+1);
                                if(length<8){
                                    System.out.println("Password must be 8 digit ");
                                    break;
                                }
                                user.add(new User(name,user_id,password,"user"));
                                SerializationUtils.saveData(user, userFile);
                                break;
                            case 2:
                                System.out.println("Enter Your name");
                                a.nextLine();
                                String name1=a.nextLine();
                                System.out.println("Enter Your Age ");
                                int age1=a.nextInt();
                                if(age1<6){
                                    System.out.println("The Age of User must be above 16 years ");
                                    break;
                                }
                                int user_id1=2+r.nextInt(900);
                                System.out.println("Your User ID is "+user_id1);
                                System.out.println("Set password for Your Account ");
                                long password1=a.nextLong();
                                long length1 =(long) (Math.log10(password1)+1);
                                if(length1<8){
                                    System.out.println("Password must be 8 digit ");
                                    break;
                                }
                                user.add(new User(name1,user_id1,password1,"staff"));
                                SerializationUtils.saveData(user, userFile);
                                break;
                            default:
                                System.out.println("Error \"Might be wrong input\"");
                                System.out.println("------------------------------------------------------------------------------------------------");
                                break;
                        }
                        break;
                    case 3:
                        boolean found=false;
                        boolean found1=false;
                        boolean found2=false;
                        System.out.println("Enter User id ");
                        int user_id1=a.nextInt();
                        for(User users:user){
                            if(users.user_id==user_id1){
                                found=true;
                                break;
                            }
                        }
                        if(!found){
                            System.out.println("User Id is not found ");
                            break;
                        }
                        System.out.println("Enter Book Id to Issue ");
                        long book_id=a.nextLong();
                        Book selected =null;
                        for(Book books:book){
                            if(books.book_id==book_id){
                                if(books.quantity>0){
                                    selected=books;
                                    found1=true;
                                    break;
                                }
                                else{
                                    System.out.println("Book is currently out of stock ");
                                }
                            }
                        }
                        if(!found1){
                            System.out.println("Book Id not found in the system ");
                            break;
                        }
                        for(IssueBook issuebooks:issuebook){
                            if((issuebooks.user_id==user_id1) && issuebooks.book_id==book_id){
                                found2=true;
                                System.out.println("You Already have this book ");
                                break;
                            }
                        }
                        if(!found2){
                            System.out.println("Enter User id (Admin) ");
                            int user_id3=a.nextInt();
                            System.out.println("Enter Password (Admin) ");
                            long pass=a.nextLong();
                            boolean is_admin=false;
                            for(User users:user){
                                if(users.login(user_id3,pass)){
                                    is_admin=true;
                                    break;
                                }
                            }
                            if(is_admin){
                                issuebook.add(new IssueBook(book_id,user_id1));
                                if(selected!=null){
                                    selected.quantity-=1;
                                    System.out.println("You successfully issued the book");
                                    SerializationUtils.saveData(issuebook, issueFile);
                                    break;
                                }
                            }
                            else{
                                System.out.println("Admin authentication failed ");
                            }
                        }
                        break;
                    case 4:
                        boolean found3=false;
                        boolean found4=false;
                        boolean found5=false;
                        System.out.println("Enter user id ");
                        int user_id3=a.nextInt();
                        for(IssueBook issuebooks:issuebook){
                            if(issuebooks.user_id==user_id3){
                                found3=true;
                                break;
                            }
                        }
                        if(!found3){
                            System.out.println("User not in issue catalog ");
                            break;
                        }
                        System.out.println("Enter Book Id ");
                        long book_id2=a.nextLong();
                        for(IssueBook issuebooks:issuebook){
                            if(issuebooks.book_id==book_id2){
                                found4=true;
                                break;
                            }
                        }
                        if(!found4){
                            System.out.println("Book Id is not issue catalog ");
                            break;
                        }
                        for(int i=0;i<issuebook.size();i++){
                            if((issuebook.get(i).book_id==book_id2) && (issuebook.get(i).user_id==user_id3)){
                                issuebook.remove(i);
                                SerializationUtils.saveData(issuebook, issueFile);
                                for(Book books:book){
                                    if(books.book_id==book_id2){
                                        books.quantity+=1;
                                        System.out.println("Book return successfully ");
                                        SerializationUtils.saveData(book, bookFile);
                                        break;
                                    }

                                }
                            }
                        }
                        break;
                    case 5:
                        System.out.println("---------------------------------- All Books -------------------------------------");
                        if (book.isEmpty()) {
                            System.out.println("No books available.");
                        } else {
                            System.out.printf("%-20s | %-20s | %-15s | %-10s | %-10s","Book Name","Author Name","Book Id","Book Price","Quantity\n");
                            System.out.println("---------------------------------------------------------------------------------");
                            for(Book books:book){
                                books.display_all();
                                System.out.println(" ");
                            }
                        }
                        break;
                    case 6:
                        System.out.println("Exiting the Library .....");
                        System.out.println("---------------------------------------------------------------------------------");
                        a.close();
                    default:
                        System.out.println("Error \"Might be wrong input\"");
                        System.out.println("---------------------------------------------------------------------------------");
                        break;
                }
            }
            catch (Exception e){
                System.out.println("Error \"Might be wrong input\"");
                System.out.println("---------------------------------------------------------------------------------");
                a.nextLine();
            }
        }
    }
}