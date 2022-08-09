package com.narayana;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.*;
class Account {
	static Scanner s = new Scanner(System.in);
	ArrayList<Long> account_number = new ArrayList<>();
	ArrayList<Long> aadhaar_number = new ArrayList<>(), mobile_number = new ArrayList<>();
	ArrayList<Double> deposit = new ArrayList<>(), withdraw_ammount = new ArrayList<>();
	ArrayList<String> usermane = new ArrayList<>();
	int age; String username, password; boolean check; long haccount_number;String name, type_of_account, gender;
	long haadhaar_number, hmobile_number;double dep, wit;String nam, typ;long adh, mob;int ag;
	String gen, usens, pass; double balence; HashMap<Long, Account> user = new HashMap<>();int b; long c;
	String ask = "y"; Pattern p = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
	Account() {
	}
	Account(String nam, String typ, long adh, long mob, int ag, String gen, ArrayList<Double> det, String usens, String pass, ArrayList<Double> wet) {
		name = nam;
		type_of_account = typ;
		haadhaar_number = adh;
		hmobile_number = mob;
		age = ag;
		gender = gen;
		username = usens;
		password = pass;
		deposit = det;
		withdraw_ammount = wet;
	}
	void intise(){
		try{
			FileInputStream fw=new FileInputStream("data.dat");
			ObjectInputStream ow=new ObjectInputStream(fw);
			long lol=ow.readLong();int i=0;
			while(i<lol){

				if(ow.readUTF().equals("Account")){
					haccount_number=ow.readLong();
					nam = ow.readUTF();
					typ=ow.readUTF();
					adh = ow.readLong();
					mob = ow.readLong();
					ag = ow.readInt();
					gen = ow.readUTF();
					usens=ow.readUTF();
					pass = ow.readUTF();
					deposit = (ArrayList) ow.readObject();
					withdraw_ammount = (ArrayList) ow.readObject();
				}
				inst();
				i++;
			}
			account_number=(ArrayList<Long>) ow.readObject();
			aadhaar_number= (ArrayList<Long>) ow.readObject();
			mobile_number=(ArrayList<Long>) ow.readObject();
			usermane=(ArrayList<String>) ow.readObject();
			ow.close();
			fw.close();
		}catch(Exception e){ }
	}

	void inst() {
		user.put(haccount_number, new Account(nam, typ, adh, mob, ag, gen, deposit, usens, pass, withdraw_ammount));
	}

   void writing(){
		try{
	         FileOutputStream fw=new FileOutputStream("data.dat");
	         ObjectOutputStream ow=new ObjectOutputStream(fw);
			  int i=0;
			ow.writeLong(account_number.size());
	        while(i<account_number.size()){
	        	ow.writeUTF("Account");
	        	ow.writeLong(account_number.get(i));
	        	ow.writeUTF(user.get(account_number.get(i)).name);
	        	ow.writeUTF(user.get(account_number.get(i)).type_of_account);
	        	ow.writeLong(user.get(account_number.get(i)).haadhaar_number);
	        	ow.writeLong(user.get(account_number.get(i)).hmobile_number);
	        	ow.writeInt(user.get(account_number.get(i)).age);
	        	ow.writeUTF(user.get(account_number.get(i)).gender);
	        	ow.writeUTF(user.get(account_number.get(i)).username);
	        	ow.writeUTF(user.get(account_number.get(i)).password);
	        	ow.writeObject(user.get(account_number.get(i)).deposit);
	        	ow.writeObject(user.get(account_number.get(i)).withdraw_ammount);
	        	i++;
	        }
			ow.writeObject(account_number);
			ow.writeObject(aadhaar_number);
			ow.writeObject(mobile_number);
			ow.writeObject(usermane);
		}
		catch (Exception e){
			e.printStackTrace();
		}
   }

	void start() {
		ask = "y";
		do {
			System.out.println("\n1. Account Creation\n2. Change details of your account\n3. Delete Account\n4. Deposit amount\n" +
					"5. Withdraw ammount\n6. Check Account Balance");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("\nEnter the option number you want to perform: ");
			try {
				b = s.nextInt();
			} catch (Exception e) {
				s.nextLine();
			}
			if (b != 1 && b != 2 && b != 3 && b != 4 && b != 5 && b != 6) {
				System.out.println("Invalid option!");
				System.out.println("please select the option number from the following");
			}
		} while (b != 1 && b != 2 && b != 3 && b != 4 && b != 5 && b != 6);
		switch (b) {
			case 1:
				check = checker("adhaar");
				if (!check && ask.equalsIgnoreCase("y")) creation();
				else if (check) System.out.println("the aadhaar number you entered is already linked with" +
						" another account pls try again later");
				break;
			case 2:
				check = checker("modify");
				if (check && ask.equalsIgnoreCase("y")) login("modify");
				break;
			case 3:
				check = checker("delete");
				if (check && ask.equalsIgnoreCase("y")) deleted();
				break;
			case 4:
				deposit.clear();
				check = checker("deposit");
				if (check && ask.equalsIgnoreCase("y")) deposit();
					break;
			case 5:
				withdraw_ammount.clear();
				check = checker("withdraw");
				if (check && ask.equalsIgnoreCase("y")) login("withdraw");
					break;
			case 6:
				check = checker("check balance");
				if (check && ask.equalsIgnoreCase("y")) login("trans");
				break;
		}
	}

	void balanced() {
		Iterator<Double> dbal = user.get(haccount_number).deposit.iterator();
		Iterator<Double> wbal = user.get(haccount_number).withdraw_ammount.iterator();
		double dsum = 0, wsum = 0;
		while (dbal.hasNext()) {
			dsum += dbal.next();
		}
		while (wbal.hasNext()) {
			wsum += wbal.next();
		}
		balence = dsum - wsum;
	}

	void login(String e) {
		String us;
		String ps;
		while (true) {
			System.out.print("Enter your username: ");
			us = s.next();
			System.out.print("Enter password: ");
			s.nextLine();
			ps = s.next();
			if (ps.equalsIgnoreCase("r")) {
				check = checker("adhaar");
				if (!check) {
					System.out.println("The aadhaar number you entered is not in our data base.. try later...");
					break;
				} else {
					s.nextLine();
					while (true) {
						System.out.print("Enter your username or u to exit: ");
						us = s.nextLine();
						if (((user.get(haccount_number)).username.equals(us))) {
							passworded();
							user.get(haccount_number).password = pass;
							System.out.println("password has been successfully updated... now you can login");
							s.nextLine();
							break;
						} else if (((user.get(haccount_number)).username.equals(us)))
							System.out.println("THe username you entered doesnt match pls try again...");
						else if (us.equalsIgnoreCase("u")) break;
					}
				}
			} else if ((!(((user.get(haccount_number).username).equals(us))) || !(((user.get(haccount_number)).password.equals(ps))))) {
				System.out.println("username or password is incorrect pls try again! or Enter r in password to reset!!");
			} else if (((user.get(haccount_number)).username.equals(us)) && ((user.get(haccount_number)).password.equals(ps))) {
				if (e.equalsIgnoreCase("modify")) modifier();
				else if (e.equalsIgnoreCase("withdraw")) withdraw();
				else if (e.equalsIgnoreCase("trans")) trans(haccount_number);
				break;
			}
		}
	}

	void modifier() {
		String mod;
		System.out.print("As of now you can only modify your mobile number\nif you want to continue press y else N: ");
		do {
			mod = s.next();
			if (!mod.equalsIgnoreCase("y") && !mod.equalsIgnoreCase("n")) {
				System.out.print("Enter Y or N only: ");
			}
		} while (!mod.equalsIgnoreCase("y") && !mod.equalsIgnoreCase("n"));
		if (mod.equalsIgnoreCase("y")) {
			while (true) {
				try {
					System.out.print("Enter new mobile number or 0 to exit: ");
					hmobile_number = s.nextLong();
					if (mobile_number.contains(hmobile_number))
						System.out.println("The mobile number you entered already exist in our data base...\npls try again...");
					if ((String.valueOf(hmobile_number).length()) != 10)
						System.out.println("Mobile number should have only 10 digits... Try again...");
					if (hmobile_number == 0) break;
					if (!mobile_number.contains(hmobile_number) && (String.valueOf(hmobile_number).length()) == 10)
						break;
				} catch (Exception e) {
					System.out.println("Enter in digits only");
					s.nextLine();
				}
			}
			if (hmobile_number != 0) {
				user.get(haccount_number).hmobile_number = hmobile_number;
				System.out.println("your Mobile number is successfully updated");
			}
		}
		inst();
	}

	void creation() {
		aadhaar_number.add(c);
		mobile();
	}

	void deposit() {
		String confirm;
		System.out.println("Minimum deposit is 200 rs!!");
		while (true) {
			System.out.print("Enter ammount or to exit: ");
			try {
				dep = s.nextDouble();
				if (dep == (double) 0) {
					System.out.println("deposit aborted");
					break;
				}
				if (dep < 200) {
					System.out.println("Amount must be greater than 199 rs");
				}
				if (dep > 199) break;
			} catch (Exception e) {
				System.out.println("Amount should only be in digits!");
				s.nextLine();
			}
		}
		if (dep != (double) 0) {
			System.out.println(dep + " rs has been successfully credited to your account");
			user.get(haccount_number).deposit.add(dep);
			System.out.print("Do you want to deposit more[Y/N]: ");
			while (true) {
				confirm = s.next();
				if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("n")) break;
				if (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n"))
					System.out.print("Enter only Y/N: ");
			}
			if (confirm.equalsIgnoreCase("y")) deposit();
			else {
				System.out.println("Desposit completed sucessfully");
				balanced();
				System.out.println("Your current balance is " + balence);
				s.nextLine();
			}
		}
		inst();
	}

	void withdraw() {

		String confirm;
		System.out.println("Minimum Withdrawl is 200 rs!!");
		while (true) {
			balanced();
			System.out.print("Enter ammount or 0 to exit: ");
			try {
				wit = s.nextDouble();
				if (wit == (double) 0) {
					System.out.println("withdrawl aborted");
					break;
				}
				if (wit < 200) System.out.println("Amount must be greater than 199 rs");
				if (wit > balence) System.out.println("You don't have enough ammount in your account");
				if (wit > 199 && wit <= balence) break;
			} catch (Exception e) {
				System.out.println("Amount should only be in digits!");
				s.nextLine();
			}
		}
		if (wit != (double) 0) {
			System.out.println(wit + " rs has been successfully debited from your account");
			user.get(haccount_number).withdraw_ammount.add(wit);
			System.out.print("Do you want to withdraw more[Y/N]: ");
			while (true) {
				confirm = s.next();
				if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("n")) break;
				if (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n"))
					System.out.print("Enter only Y/N: ");
			}
			if (confirm.equalsIgnoreCase("y")) withdraw();
			else {
				System.out.println("Withdrawl completed sucessfully");
				balanced();
				System.out.println("Your current balance is " + balence);
				s.nextLine();
			}
		}
		inst();
	}

	void mobile() {
		while(true){
			exceptioner("mobile number");
			if ((String.valueOf(hmobile_number)).length() != 10) {
				System.out.println("mobile number should contain only ten numbers.. Try again");
				question();
			} else if (mobile_number.contains(hmobile_number)) {
				System.out.println("The number you entered is already given...");
				question();
			}
			else if(ask.equalsIgnoreCase("y") && (String.valueOf(hmobile_number).length())==10){
				mobile_number.add(hmobile_number);
				biodata();
				break;
			}
			if(ask.equalsIgnoreCase("n")) break;
		}
		if(ask.equalsIgnoreCase("n"))
		aadhaar_number.remove(haadhaar_number);
	}

	void biodata() {
		s.nextLine();
		nam = schecker();
		while (true) {
			System.out.print("Enter age: ");
			try {
				ag = s.nextInt();
				break;
			} catch (Exception e) {
				System.out.println("Age can only be in digits... try again");
				s.nextLine();
			}
		}
		System.out.print("1.Male\n2.Female\nEnter M for male, F for female:");
		s.nextLine();
		do {
			gen = s.nextLine();
			if (!gen.equalsIgnoreCase("m") && !gen.equalsIgnoreCase("f")) {
				System.out.print("Enter only M or F: ");
			}
		} while (!gen.equalsIgnoreCase("m") && !gen.equalsIgnoreCase("f"));
		switch (gen.toUpperCase()) {
			case "M":
				gen = "Male";
				break;
			case "F":
				gen = "Female";
				break;
		}
		typacc();
	}

	void typacc() {
		System.out.print("Enter s for savings c for current account\nEnter your option: ");
		do {
			typ = s.nextLine();
			if (!typ.equalsIgnoreCase("s") && !typ.equalsIgnoreCase("c")) {
				System.out.print("pls Enter s or c only: ");
			}
		} while (!typ.equalsIgnoreCase("s") && !typ.equalsIgnoreCase("c"));
		switch (typ.toUpperCase()) {
			case "s":
				typ = "savings";
				break;
			case "c":
				typ = "current";
				break;
		}
		genacc();
	}

	void genacc() {
		do {
			System.out.print("Enter username of length greater than 5 and less than 26 characters: ");
			usens = s.nextLine();
			if (usermane.contains(usens))
				System.out.println("Username you have choosen is already taken.. try agin...");
			if (usens.length() < 5) System.out.println("username is small try again...");
			if (usens.length() > 25) System.out.println("username is too big try again...");
		} while (usermane.contains(usens) || usens.length() <= 5 || usens.length() >= 26);
		usermane.add(usens);
		passworded();
		do {
			haccount_number = ThreadLocalRandom.current().nextLong(111111111, 999999999);
		} while (account_number.contains(haccount_number));
		account_number.add(haccount_number);
		System.out.println("Your account has been sucessfully created\nYour account number is " + haccount_number);
		inst();
	}

	void passworded() {
		System.out.println("We recommend you to keep password in the combination of alpha numeric and special characters");
		do {
			System.out.print("\nthe password should be of length greater than 5 and less than 26 characters\nCan be alpha numeric and can contain @#$\nEnter your desired password: ");
			pass = s.next();
			if (pass.length() < 5)
				System.out.println("password length should be in between 5 to 26 characters!! try again");
		} while (!pass.matches(".*[0-9]+.*") || !pass.matches(".*[@#$]+.*") || pass.length() <= 5 || pass.length() >= 26);
	}

	String schecker() {
		boolean checked;
		String str;
		System.out.print("Enter your name: ");
		do {
			str = s.nextLine();
			Matcher m = p.matcher(str);
			checked = m.find();
			if (checked) {
				System.out.println(str + " shouldn't contain special characters or number");
				System.out.print("Enter Name again: ");
			}
		} while (checked);
		return str;
	}

	boolean checker(String e) {
		if (e.equalsIgnoreCase("adhaar")) {
			do {
				exceptioner("aadhaar number");
				if ((String.valueOf(haadhaar_number)).length() != 12) {
					System.out.println("Aadhaar number should have only 12 digits but recieved " + (String.valueOf(c)).length() + "digits");
					question();
				}
			} while ((String.valueOf(haadhaar_number)).length() != 12 && ask.equalsIgnoreCase("Y"));
			return aadhaar_number.contains(haadhaar_number);
		} else {
			do {
				exceptioner("Account number");
				if (!account_number.contains(haccount_number)) {
					System.out.println("The Account number you enter doesn't exist in our data base!");
					question();
				}
			} while (!account_number.contains(haccount_number) && ask.equalsIgnoreCase("Y"));
			return account_number.contains(haccount_number);
		}

	}

	void question() {
		do {
			System.out.print("If you want to continue press Y else N: ");
			ask = s.next();
			if (!ask.equalsIgnoreCase("y") && !ask.equalsIgnoreCase("n"))
				System.out.println("Choose only Y or N");
		} while (!ask.equalsIgnoreCase("y") && !ask.equalsIgnoreCase("n"));
	}

	void exceptioner(String e) {
		while (true) {
			try {
				System.out.print("Enter " + e + ": ");
				c = s.nextLong();
				break;
			} catch (Exception d) {
				System.out.println(e + " can only be in digits try again");
				s.nextLine();
			}
		}
		if (e.equalsIgnoreCase("Account number")) haccount_number = c;
		else if (e.equalsIgnoreCase("mobile number")) {
			mob = c;
			hmobile_number = c;
		} else haadhaar_number = c;
	}

	void trans(long acc) {
		while (!account_number.contains(acc) && acc != 0) {
			System.out.print("Account number you entered is not found in our data base\npls enter a valid account number or 0 to exit: ");
			acc = s.nextLong();
		}
		if (acc != 0) {
			balanced();
			System.out.println("\nYour current Account balance is " + balence);
			Account aden = user.get(acc);
			System.out.println("\n******************************YOUR LAST FIVE TRANSACTIONS******************************");
			System.out.printf("%-35s--%35s\n", "CREDIT", "DEBIT");
			for (int i = 0; i < 5; i++) {
				try {
					aden.deposit.get(i);
					System.out.printf("%-35f--", aden.deposit.get(i));
				} catch (Exception e) {
					System.out.printf("%-35f--", 0.0);
				}
				try {
					aden.withdraw_ammount.get(i);
					System.out.printf("%35f\n", aden.withdraw_ammount.get(i));
				} catch (Exception e) {
					System.out.printf("%35f\n", 0.0);
				}
			}
		}
	}

	void deleted() {
		s.nextLine();
		login("delete");
		System.out.print("Deleting your account is irreverible and Your ammount will be withdrawn and given to you after deletion\n" +
				"Do you want to delete your account[Y/N]: ");
		while (true) {
			String confirm = s.next();
			if (confirm.equalsIgnoreCase("y")) {
				do {
					System.out.print("Re-Enter your password to delete or d to exit: ");
					pass = s.next();
					if (!((((user.get(haccount_number)).password).equals(pass))))
						System.out.println("Password is incorrect.... Try again..");
				} while (!((((user.get(haccount_number)).password).equals(pass))) || pass.equalsIgnoreCase("d"));
				if (pass.equalsIgnoreCase("d")) break;
				else {
					System.out.println("Acoount has been deleted successfully!!");
					user.remove(haccount_number);
					account_number.remove(haccount_number);
					break;
				}
			} else if (!(confirm.equalsIgnoreCase("n")) && !(confirm.equalsIgnoreCase("y")))
				System.out.print("pls enter Y/N only:");
			else {
				System.out.println("Account deletion is terminated");
				break;
			}
		}
	}
}

public class Main {

    public static void main(String[] args) {
    	System.out.println("************************************WELCOME TO EDHO BANK************************************************\n");
        Scanner s=new Scanner(System.in);
        Account ad=new Account();
        String ans;
        ad.intise();
	    do {
            ad.start();
            System.out.print("\nDo you want to do other operations[Y/N}: ");
            do {
                ans = s.next();
                if (!ans.equalsIgnoreCase("Y") && !ans.equalsIgnoreCase("N")) {
                    System.out.print("Choose Y for yes or N for no: ");
                }
            } while (!ans.equalsIgnoreCase("Y") && !ans.equalsIgnoreCase("N"));
        }while(ans.equalsIgnoreCase("y"));
	    ad.writing();
        System.out.println("\n************************************THANK YOU************************************************");
    }
}