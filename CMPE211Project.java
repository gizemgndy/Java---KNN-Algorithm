package timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CMPE211Project  {
	static String csvFile;
	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 500, 400);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		/******************************/
		//adding text fields to the frame
		JTextField txt = new JTextField();
		JTextField txt2 = new JTextField();
		JTextField txt3 = new JTextField();
		JTextField txt4 = new JTextField();
		JTextField txt5 = new JTextField();
		
		
		txt.setBounds(30, 100, 100, 30);
		txt2.setBounds(140, 100, 100, 30);
		txt3.setBounds(250, 100, 100, 30);
		txt4.setBounds(360, 100, 100, 30);
		txt5.setBounds(50, 300, 100, 30);
		
		frame.add(txt);
		frame.add(txt2);
		frame.add(txt3);
		frame.add(txt4);
		frame.add(txt5);
		
		/**********************************************/
		//creating file chooser before the frame is opened
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		
		//if the file is chosen put the path into the path string
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File myFile = fileChooser.getSelectedFile();
			csvFile = myFile.getAbsolutePath();
		}
		//Creating the arraylist to put every line in the data 
		ArrayList<String[]> lines = new ArrayList<String[]>();
		
		//for every line in the data
		String thisLine; 
		
		//creating fileinputstream to read data
		FileInputStream fis = null;
			try {
				fis = new FileInputStream(csvFile);
			} catch (FileNotFoundException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
				
				}
			DataInputStream myInput = new DataInputStream(fis);
		

			try {
				while ((thisLine = myInput.readLine()) != null) {
					lines.add(thisLine.split(","));
		     
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		/******************************************************/
		
		//to convert the arraylist into a string array	
		String[][] StringArray= new String[lines.size()][0];
		lines.toArray(StringArray);
		
		//converting the String array to double array
		double[][] array = new double[StringArray.length][StringArray[0].length];
		ArrayConvert(StringArray, array);
			
		//Button to calculate
		JButton button = new JButton("Calculate");
		button.setBounds(250,300,180,30);
		frame.add(button);
		
		
		/*****************************************/
		//adding the labels to the frame
		
		JLabel lbl = new JLabel("Social");
		JLabel lbl2 = new JLabel("Algorithm");
		JLabel lbl3 = new JLabel("GPA");
		JLabel lbl4 = new JLabel("Age");
		JLabel lbl5 = new JLabel("K");
		JLabel lbl6 = new JLabel();
		
		lbl.setBounds(50, 70, 100, 30);
		lbl2.setBounds(160, 70, 100, 30);
		lbl3.setBounds(270, 70, 100, 30);
		lbl4.setBounds(380, 70, 100, 30);
		lbl5.setBounds(50, 270, 100, 30);
		lbl6.setBounds(270, 270, 100, 30);
		
		frame.add(lbl);
		frame.add(lbl2);
		frame.add(lbl3);
		frame.add(lbl4);
		frame.add(lbl5);
		frame.add(lbl6);
		/************************************************/
		//when the button is clicked
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					/****************************************/
	       			//Get the data from the textfields and put them in an array
				
					double[] newData = new double[4];
	       			newData[0] = Double.valueOf(txt.getText());
	       			newData[1] = Double.valueOf(txt2.getText());
	       			newData[2] = Double.valueOf(txt3.getText());
	       			newData[3] = Double.valueOf(txt4.getText());
	       		
	       			/****************************************/
	       			//Calculate the distances for every line and put them into an array
	       			double[] distanceArray = new double[array.length];
	       			distanceCalc(array, newData, distanceArray);
	       			
	       			/*****************************************/
	       			//to manipulate the 1s and 0s
	       			int[] binaryArr = new int[distanceArray.length];
	       			for (int i = 0; i < distanceArray.length; i++) {
	       			
	       				binaryArr[i] = (int)array[i][4];
	       			}
	       			
	       			/********************************************/
	       			//sort the distanceArray and the binaryArray
	       			sort(distanceArray,0,distanceArray.length-1,binaryArr);
	       		
	       			//get K from the textField
	       			int k = Integer.parseInt(txt5.getText());
	       			
	       			//number of ones
	       			int count = 0;
	       			
	       			//count the number of ones
	       			for (int i = 0; i < k; i++) if(binaryArr[i] == 1) count++;
	       		
	       			JOptionPane jp = new JOptionPane();
	       			//if there are more ones than zeros hire that person
	       			if(count>=k-count) {
	       				frame.getContentPane().setBackground(Color.GREEN);
	       				
	       				jp.showMessageDialog(null, "The person is hired!");
	       			}
	       			
	       			else {
	       				frame.getContentPane().setBackground(Color.RED);
	       				jp.showMessageDialog(null, "The person is NOT hired!");
	       			}
	       				
	            	
	            
	            
				
			}
		});
		
		frame.setVisible(true);
		
		
	}
	
	
	/***********************************************************/
	static int partition(double arr[], int low, int high,int[] arr2) 
    { 
        double pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than the pivot 
            if (arr[j] < pivot) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                double temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
                int temp2 = arr2[i];
                arr2[i] = arr2[j];
                arr2[j] = temp2;
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        double temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
        
        int temp2 = arr2[i+1];
        arr2[i+1] = arr2[high];
        arr2[high] = temp2;
        return i+1; 
    } 
	
	/***********************************************************/
	static void sort(double arr[], int low, int high,int[] arr2) 
    { 
        if (low < high) 
        { 
            /* pi is partitioning index, arr[pi] is  
              now at right place */
            int pi = partition(arr, low, high,arr2); 
  
            // Recursively sort elements before 
            // partition and after partition 
            sort(arr, low, pi-1,arr2); 
            sort(arr, pi+1, high,arr2); 
        } 
    }
	

	/**********************************************************/
	
	
	static void ArrayConvert(String[][] StringArray, double[][] array )
	{
		for (int i = 0; i < StringArray.length;i++) {
				for (int j = 0; j < StringArray[0].length; j++) {
					array[i][j] = Double.valueOf(StringArray[i][j]);
				}
		}
	}
	/**************************************************************/
	
	static void distanceCalc(double[][] array, double[] newData,double[] distanceArray) {
		for (int j = 0; j < array.length; j++) {
				double sum = 0;
			
				for (int k = 0; k< array[0].length-1;k++) {
					
					sum += Math.pow(newData[k]-array[j][k], 2);
						
				}		
				distanceArray[j] = Math.sqrt(sum);
				
			}
	}
	/***************************************************************/
}
