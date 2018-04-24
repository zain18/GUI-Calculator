/*
 * Walid zein
 * Calculator 
 */
package calculator;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.lang.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Icon;


class Calculator extends JFrame implements ActionListener {
    
    // graphical features' variables
    Checkbox binary, hex, octal, decimal;
    int radix = 2;
    private int toStoreFeatures = 0;
    JButton g = new JButton("g");
	int numsize = 16;
    TextArea display = new TextArea("", 20,0, TextArea.SCROLLBARS_NONE);
    private String[] buttonNames = {
			"Hex","Dec","Oct","Bin",
	        "Lsh","Rsh","Or","Xor","Not","And",
            "↑", "Mod", "CE", "C", "Backspace", "/",
            "A", "B", "7", "8", "9", "*",
            "C", "D", "4", "5", "6", "-",
            "E", "F", "1", "2", "3", "+",
            "(", ")", "+/-", "0", ".", "="
             }; 
	
    private Component[] features = new Component[50];
	private JPanel bReg0 = new JPanel();

    private JPanel bReg1 = new JPanel();
//    private JPanel bReg2 = new JPanel();
//    private JPanel bReg4 = new JPanel();
    private JPanel bReg4 = new JPanel();
//    private JPanel nonFunctionalPanel = new JPanel();
    private JPanel labelReg = new JPanel();
    private JPanel buttonArea = new JPanel();
	private JPanel bottomArea = new JPanel();
	private JButton hexb = new JButton("Hex");
	private JButton decb = new JButton("Dec");
	private JButton oct = new JButton("Oct");
	private JButton bin = new JButton("Bin");
	
    // variables to manage operations
    private String textOnScreen = "0";
    private String memory = "0";
    private boolean validOp = false;
    private boolean concDigit = false;// decides if the digit pressed by the user must be concatenate or simply replace the existing one
    private String errorMessage;
    private String currentOp = null;
    private Operation proceedOp = null;// object wich will be created each time to proceed a new operation
    private boolean Bakspace = true;
    private boolean currCharIsCom = false;
    private boolean isDouble = false;// if the number on screen is double(to check if it already has the comma)

    public Calculator() {
        super("Calculator");
		
		Color c = new Color(232,232,230	);
		Color bc = new Color(246,246,246);
	
		bReg0.setLayout(new GridLayout(4,1,0,0));
		bReg0.add(nextButtonGrid(1, 1, 0, 10));
		bReg0.add(nextButtonGrid(1, 1, 0, 10));
		bReg0.add(nextButtonGrid(1, 1, 0, 10));
		bReg0.add(nextButtonGrid(1, 1, 0, 10));    
        bReg1.setLayout(new GridLayout(7, 1, 0, 0));    
        bReg1.add(nextButtonGrid(1, 6, 0, 10));
        bReg1.add(nextButtonGrid(1, 6, 0, 10));
        bReg1.add(nextButtonGrid(1, 6, 0, 10));
        bReg1.add(nextButtonGrid(1, 6, 0, 10));
        bReg1.add(nextButtonGrid(1, 6, 0, 10));
        bReg1.add(nextButtonGrid(1, 6, 0, 10));
        
        bReg4.setLayout(new GridLayout(1, 5, 0, 0));
        
		JButton keyPButton = new JButton();
		try
		{
			ImageIcon keyicon = new ImageIcon(ImageIO.read(getClass().getResource("keyp.png")));
			keyPButton.setIcon(keyicon);
		}
		catch (Exception ex) {
			System.out.println(ex);
	
		}
        keyPButton.setBackground(c);
		keyPButton.setBorder(new LineBorder(c,3));
		keyPButton.setPreferredSize(new Dimension(50,50));
		Font newFont = new Font(keyPButton.getFont().getName(), keyPButton.getFont().getStyle() & ~Font.BOLD,20 );
		keyPButton.setFont(newFont);
        bReg4.add(keyPButton);
        
        JButton dots = new JButton();
		try
		{
			ImageIcon dotsicon = new ImageIcon(ImageIO.read(getClass().getResource("dots.png")));
			dots.setIcon(dotsicon);
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
        dots.setBackground(c);
		dots.setBorder(new LineBorder(c,3));
		dots.setPreferredSize(new Dimension(50,50));
		dots.setFont(newFont);
        bReg4.add(dots);
        
        JButton word = new JButton("WORD");
        word.setBackground(c);
		word.setBorder(new LineBorder(c,3));
		word.setPreferredSize(new Dimension(50,50));
		word.setFont(newFont);   
        bReg4.add(word);
		word.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
        //Execute when button is pressed
				if(word.getText().equals("WORD"))
				{
					int num = Integer.parseInt(textOnScreen);
					String binary = Integer.toBinaryString(num);
					int binlen = binary.length();
					String newbinary = "";
					
					if(binlen > 8)
					{
						newbinary = binary.substring(binlen-8);
					}
					else
						newbinary = binary;
					byte bnum = 0;
					bnum  = (byte)Integer.parseInt(newbinary,2);
					textOnScreen = Byte.toString(bnum);	
					word.setText("Byte");
					numsize = 8;
					
				}
				else if(word.getText().equals("Byte"))
				{
					
					word.setText("QWORD");
					numsize = 64;
				}
				else if(word.getText().equals("QWORD"))
				{
					word.setText("DWORD");
					numsize = 32;
				}
				else if(word.getText().equals("DWORD"))
				{
					word.setText("WORD");
					numsize = 16;
				}
				affiche(textOnScreen);
				updateButtons();
			}
		});
        
        JButton ms = new JButton("MS");
        ms.setBackground(c);
		ms.setBorder(new LineBorder(c,3));
		ms.setPreferredSize(new Dimension(50,50));
		ms.setFont(newFont);
        bReg4.add(ms);
        
        JButton m = new JButton("M-");
        m.setBackground(c);
		m.setBorder(new LineBorder(c,3));
		m.setPreferredSize(new Dimension(50,50));
        m.setFont(newFont);
        bReg4.add(m);
        
        
        
        
		
		
		//buttonArea.setBackground(new Color(232,232,230	));
        buttonArea.setLayout(new BorderLayout(0, 0));
		
        buttonArea.add(bReg4, BorderLayout.NORTH);
		buttonArea.add(bReg1, BorderLayout.SOUTH);
		bottomArea.setLayout(new BorderLayout());
		bottomArea.add(bReg0, BorderLayout.NORTH);
		bottomArea.add(buttonArea, BorderLayout.SOUTH);
		
        display.setBackground(Color.WHITE);
        display.setEditable(false);
		labelReg.setLayout(new BorderLayout());
		try
		{
			ImageIcon progicon = new ImageIcon(ImageIO.read(getClass().getResource("programmer.png")));
			JLabel programmer = new JLabel(progicon);
			JPanel prog = new JPanel();
			prog.add(programmer);
			labelReg.add(prog, BorderLayout.NORTH);
		}
		catch (Exception ex) {
			System.out.println(ex);
	
		}
		
		
        labelReg.add(display, BorderLayout.SOUTH);

//        display.setMinimumSize(new Dimension(1000, 1000));
        
        
        setLayout(new BorderLayout());
        add(labelReg, BorderLayout.NORTH);
        add(bottomArea, BorderLayout.SOUTH);
        
		setMinimumSize(new Dimension(200,250));
		setBackground(c);	
        
        for (int i = 0; i < buttonNames.length; i++) {
            
            ((JButton) features[i]).addActionListener(this);
            

            System.out.println("Still in! " + i);
        }


        pack();
        setVisible(true);
    }

    public static class Operation {
        public String param1;
        public String param2;
        public String currentOp;
        public String percent;
        public String lastOp;

        private Operation(String par1, String op, String par2) {

            param1 = par1;
            param2 = par2;
            currentOp = op;
            percent = null;
            lastOp = null;// initialising the parameters of the object operation
        }

        private String eval() {
            return param1;
        }

        private void replaceOp(String op) {
            currentOp = op;
        }
    }

	
    public void actionPerformed(ActionEvent e) {
        boolean done = false;
        int k;
              //put a common letter in front of the names of all buttons with the same methods
              // would have made their identification much easier and more general; but the idea came to me after this implementation a complicated can
        for (k = 0; k < features.length && !done; k++) {
            if (e.getSource() == features[k]) {
                done = true;
                k--;
            }
        }
        String repere = buttonNames[k];
        try {
            Double.parseDouble(textOnScreen);
            try {
                if (Integer.parseInt(repere) < 10) {
					int num = Integer.parseInt(textOnScreen);
					String binarynum = Integer.toBinaryString(num);
					if(binarynum.length() < numsize-4)
						manageDigits(repere);
                }
            } catch (NumberFormatException ex) {
				if(repere.contains("Hex"))
				{
					activateHex();
				} else if (repere.contains("Dec")) {
					activateDec();
				} else if (repere.contains("Oct")) {
					activateOct();
                } else if (repere.contains("Bin")) {
					activateBin();
				} else if (repere.equals("M")) {
                    manageMemory(repere);
                } else if (repere.charAt(0) == 'B' || repere.charAt(0) == 'C') {
                    manageErasers(repere);
                } else if (repere.charAt(0) == ',') {
                    comma();
                } else if (repere.charAt(0) == '=') {
                    equal();
                } else if (repere.charAt(0) == '%') {
                    percentage();
                } else if (repere.length() == 1 || repere.equals("Mod")) {
                    proceedOp(repere);
                } else {
                    oneParamOp(repere);
                }
            }
        } catch (RuntimeException r) {
            if (repere.charAt(0) == 'C') {
                textOnScreen = "0";
                manageErasers(repere);
            }
        }
		updateButtons();
    }
	private void activateHex()
	{
		for(int i  =0; i < features.length; i++)
		{
			((JButton)features[i]).setEnabled(true);
		}
	}
	private void activateDec()
	{
		for(int i  =0; i < buttonNames.length; i++)
		{
			if(buttonNames[i].equals("A") || buttonNames[i].equals("B") || buttonNames[i].equals("C") || buttonNames[i].equals("D") || buttonNames[i].equals("E") || buttonNames[i].equals("F"))
			{
				((JButton)features[i]).setEnabled(false);
			}
			else
			{
				((JButton)features[i]).setEnabled(true);
			}
		}
	}
	private void activateOct()
	{
		for(int i  =0; i < buttonNames.length; i++)
		{
			if(buttonNames[i].equals("A") || buttonNames[i].equals("B") || buttonNames[i].equals("C") || buttonNames[i].equals("D") || buttonNames[i].equals("E") || buttonNames[i].equals("F") || buttonNames[i].equals("8") || buttonNames[i].equals("9"))
			{
				((JButton)features[i]).setEnabled(false);
			}
			else
			{
				((JButton)features[i]).setEnabled(true);
			}
		}
	}
	private void activateBin()
	{
		for(int i  =0; i < buttonNames.length; i++)
		{
			if(buttonNames[i].equals("A") || buttonNames[i].equals("B") || buttonNames[i].equals("C") || buttonNames[i].equals("D") || buttonNames[i].equals("E") || buttonNames[i].equals("F") || buttonNames[i].equals("2") || buttonNames[i].equals("3") || buttonNames[i].equals("4") || buttonNames[i].equals("5") || buttonNames[i].equals("6") || buttonNames[i].equals("7") || buttonNames[i].equals("8") || buttonNames[i].equals("9") )
			{
				((JButton)features[i]).setEnabled(false);
			}
			else
			{
				((JButton)features[i]).setEnabled(true);
			}
		}
	}
    private JPanel nextButtonGrid(int lon, int larg, int hspace, int vspace) {
        JPanel toReturn = new JPanel();
        int r = 0, i;
		Color c = new Color(232,232,230	);
		Color bc = new Color(246, 246, 246);
		Color lbc = new Color(237,237,237);
		toReturn.setBackground(c);
		//Font newFont = new Font()
        toReturn.setLayout(new GridLayout(lon, larg, hspace, vspace));
        for (i = 0; i < lon * larg; i++) {
            r = i + toStoreFeatures;
		
				if(buttonNames[r].equals("↑"))
				{
					features[r] = new JButton("↑");
					try
					{
						ImageIcon upicon = new ImageIcon(ImageIO.read(getClass().getResource("uparrow.png")));
						((JButton)features[r]).setIcon(upicon);
					}
					catch (Exception ex) {
						System.out.println(ex);
				
					}
					features[r].setSize(15, 15);
					features[r].setBackground(bc);
					((JButton)features[r]).setBorder(new LineBorder(c,3));
					((JButton)features[r]).setPreferredSize(new Dimension(50,50));
					
				}
				else if(buttonNames[r].equals("Backspace"))
				{
					features[r] = new JButton("Backspace");
					try
					{
						ImageIcon upicon = new ImageIcon(ImageIO.read(getClass().getResource("backspace.png")));
						((JButton)features[r]).setIcon(upicon);
					}
					catch (Exception ex) {
						System.out.println(ex);
				
					}
					features[r].setSize(15, 15);
					features[r].setBackground(bc);
					((JButton)features[r]).setBorder(new LineBorder(c,3));
					((JButton)features[r]).setPreferredSize(new Dimension(50,50));
					
				}
				else if(buttonNames[r].contains("Hex") || buttonNames[r].contains("Dec") || buttonNames[r].contains("Oct") || buttonNames[r].contains("Bin") )
				{
					features[r] = new JButton(buttonNames[r]);
					features[r].setBackground(c);
					((JButton)features[r]).setBorder(new LineBorder(c,3));
					((JButton)features[r]).setHorizontalAlignment(SwingConstants.LEFT);
					Font newFont = new Font(features[r].getFont().getName(), features[r].getFont().getStyle(),16 );
					features[r].setFont(newFont);
					
					
				}
				else
				{
					features[r] = new JButton(buttonNames[r]);
					
					
					features[r].setBackground(bc);
					Font newFont = new Font(features[r].getFont().getName(), features[r].getFont().getStyle(),16 );
					features[r].setFont(newFont);
					features[r].setSize(15, 15);
					
					
					//((JButton)features[r]).setBorderPainted(false);
					((JButton)features[r]).setBorder(new LineBorder(c,3));
					((JButton)features[r]).setPreferredSize(new Dimension(50,50));
					//((JButton)features[r]).setBorder(new LineBorder(Color.BLACK));
					//((JButton)features[r]).setFocusPainted(true);
					
				}
				toReturn.add(features[r]);
				
            
        }
        toStoreFeatures += i;
        return toReturn;
    }

    private JPanel enrobComponent(JPanel toEnrob, boolean enrobNorth, boolean enrobEast, boolean enrobSouth, boolean enrobWest) {
        JPanel toReturn = new JPanel();
        toReturn.setLayout(new BorderLayout(10, 10));
        if (enrobNorth) {
            toReturn.add(new JLabel(), BorderLayout.NORTH);
        }
        if (enrobEast) {
            toReturn.add(new JLabel(), BorderLayout.EAST);
        }
        if (enrobSouth) {
            toReturn.add(new JLabel(), BorderLayout.SOUTH);
        }
        if (enrobWest) {
            toReturn.add(new JLabel(), BorderLayout.WEST);
        }
        toReturn.add(toEnrob, BorderLayout.CENTER);
        return toReturn;
    }

    private void oneParamOp(String op) {// sqrt,1/x,+/-
        if (op.equals("+/-")) {
            char c = ' ';
            for (int i = 0; i < textOnScreen.length(); i++) {
                if (textOnScreen.charAt(i) == 'E') {
                    c = 'E';
                }
            }
            if (c != 'E') {
                if (textOnScreen.charAt(0) == '-') {
                    textOnScreen = textOnScreen.substring(1, textOnScreen.length());
                } else if (!textOnScreen.equals("0")) {
                    textOnScreen = "-" + textOnScreen;
                }
            } else {
                textOnScreen = eval(textOnScreen, op);
            }
            affiche(textOnScreen);
        } else {
            textOnScreen = eval(textOnScreen, op);
            affiche(textOnScreen);
        }
        if ((proceedOp != null)) {
            if (!("=".equals(proceedOp.lastOp) && (op.equals("+/-")))) {
                proceedOp.param2 = textOnScreen;
            }
        }

        if (!(op.equals("+/-"))) {
            concDigit = false;
        }
    }

    private void proceedOp(String op) {// +,-,*,/
        if (proceedOp == null) {
            replaceOp(op);
        } else if (proceedOp.param2 == null) {
            proceedOp.replaceOp(op);
        } else if (("=").equals(proceedOp.lastOp)) {
            proceedOp = new Operation(textOnScreen, op, null);
        } else {
            textOnScreen = eval(proceedOp.param1, proceedOp.currentOp, proceedOp.param2);
            replaceOp(op);
            affiche(textOnScreen);
        }
        concDigit = false;
        isDouble = false;
    }

    private void percentage() {
        if (proceedOp == null) {
            textOnScreen = "0";
            affiche(textOnScreen);
        } else if (proceedOp.param2 == null) {
            if (proceedOp.percent == null) {
                proceedOp.percent = eval(proceedOp.param1, "*", "0.01");
                textOnScreen = proceedOp.param2 = eval(proceedOp.param1, "*", proceedOp.percent);
            } else {

                textOnScreen = proceedOp.param2 = eval(proceedOp.param1, "*", proceedOp.percent);
            }
            affiche(textOnScreen);
        } else {
            if (proceedOp.lastOp == "=") {
                proceedOp.percent = eval(proceedOp.param1, "*", "0.01");
                textOnScreen = proceedOp.param2 = eval(textOnScreen, "*", proceedOp.percent);
                affiche(textOnScreen);
            } else {
                proceedOp.percent = eval(proceedOp.param2, "*", "0.01");
                textOnScreen = proceedOp.param2 = eval(proceedOp.param1, "*", proceedOp.percent);
                affiche(textOnScreen);
            }
        }
        if (proceedOp != null) {
            proceedOp.lastOp = null;
        }
        concDigit = false;
    }

    private void comma() {
        if (isDouble == false) {
            if (concDigit == false) {
                textOnScreen = "0";
            }
            affiche(textOnScreen);
            isDouble = true;
            currCharIsCom = true;
            concDigit = true;
        }
    }
  

    private void manageMemory(String memInfo) {
        if ((memInfo.charAt(memInfo.length() - 1)) == 'C') {
            memory = "0";
            ((JLabel) features[23]).setText("              ");
        } else if ((memInfo.charAt(memInfo.length() - 1)) == 'R') {
            textOnScreen = memory;
            affiche(textOnScreen);
        } else if ((memInfo.charAt(memInfo.length() - 1)) == 'S') {
            memory = textOnScreen;
            if (Double.parseDouble(memory) != 0) {
                ((JLabel) features[23]).setText("     M");
            }
        } else if (memory != null) {
            memory = eval(memory, "+", textOnScreen);
            if (Double.parseDouble(memory) != 0) {
                ((JLabel) features[23]).setText("     M");
            }
        }
        concDigit = false;
        if (Double.parseDouble(memory) == 0) {
            ((JLabel) features[23]).setText("              ");
        }

    }

    private void manageErasers(String erInfo) {
        if ((erInfo.equals("Backspace")) == true && concDigit) {
            if (currCharIsCom) {
                currCharIsCom = false;
            } else if (textOnScreen.length() == 1 || (textOnScreen.length() == 2 && textOnScreen.charAt(0) == '-')) {
                textOnScreen = "0";
                affiche(textOnScreen);
                concDigit = false;
            } else {
                if (currCharIsCom) {
                    currCharIsCom = false;
                } else {
                    if (textOnScreen.charAt(textOnScreen.length() - 2) == ',') {
                        isDouble = false;
                    }
                    textOnScreen = textOnScreen.substring(0, textOnScreen.length() - 1);
                    affiche(textOnScreen);
                }
            }
            if (!(proceedOp == null)) {
                proceedOp.param2 = textOnScreen;
            }
        } else if ((erInfo.equals("CE")) == true) {
            if (proceedOp != null && proceedOp.lastOp != "=") {
                proceedOp.param2 = "0";
            }
            textOnScreen = "0";
            affiche(textOnScreen);
            isDouble = false;
            concDigit = false;
        } else if (!(erInfo.equals("Backspace"))) {
            proceedOp = null;
            textOnScreen = "0";
            affiche(textOnScreen);
            isDouble = false;
            concDigit = false;
        }
    }

    private void affiche(String toPrint) {
        boolean printComma = true;
        if (toPrint == null) {
            System.out.println("nullpointer");
        }
        for (int i = 0; i < toPrint.length(); i++) {
            if (toPrint.charAt(i) == '.') {
                printComma = false;
            }
        }
        if (printComma) {
            toPrint += ".";
        }
        String space = display.getText();
        for (int j = 0; toPrint.length() < 50; j++) {
            toPrint = " " + toPrint;
        }
        display.setText(updateComma(toPrint));
	}
	private void updateButtons()
	{
		String todisplay = textOnScreen.replaceAll("[^\\d-]", "");
		long num = Long.parseLong(todisplay);
		for(int i  =0; i < buttonNames.length; i++)
		{
			if(buttonNames[i].contains("Hex"))
			{
				
				if(numsize == 8)
				{
					((JButton)features[i]).setText("Hex   "+ Integer.toHexString((byte)num & 0xFF).toUpperCase());
				}
				else if(numsize == 16)
				{
					((JButton)features[i]).setText("Hex   "+ Integer.toHexString((short)num & 0xFFFF).toUpperCase());
				}
				
				else if(numsize == 32)
				{
					((JButton)features[i]).setText("Hex   "+ Integer.toHexString((int)num & 0xFFFFFFFF).toUpperCase());
				}
				else if(numsize == 64)
				{
					((JButton)features[i]).setText("Hex   "+ Long.toHexString(num).toUpperCase());
				}
		
			}
			else if(buttonNames[i].contains("Dec"))
			{
				((JButton)features[i]).setText("Dec   " + num);
		
			}
			else if(buttonNames[i].contains("Oct"))
			{
				if(numsize == 8)
				{
					((JButton)features[i]).setText("Oct   "+ Integer.toOctalString((byte)num & 0xFF).toUpperCase());
				}
				else if(numsize == 16)
				{
					((JButton)features[i]).setText("Oct   "+ Integer.toOctalString((short)num & 0xFFFF).toUpperCase());
				}
				
				else if(numsize == 32)
				{
					((JButton)features[i]).setText("Oct   "+ Integer.toOctalString((int)num & 0xFFFFFFFF).toUpperCase());
				}
				else if(numsize == 64)
				{
					((JButton)features[i]).setText("Oct   "+ Long.toOctalString(num).toUpperCase());
				}
		
			}
			else if(buttonNames[i].contains("Bin"))
			{
				if(numsize == 8)
				{
					((JButton)features[i]).setText("Bin   "+ Integer.toBinaryString((byte)num & 0xFF).toUpperCase());
				}
				else if(numsize == 16)
				{
					((JButton)features[i]).setText("Bin   "+ Integer.toBinaryString((short)num & 0xFFFF).toUpperCase());
				}
				
				else if(numsize == 32)
				{
					
					((JButton)features[i]).setText("Bin   "+ Integer.toBinaryString((int)num & 0xFFFFFFFF).toUpperCase());
				}
				else if(numsize == 64)
				{
					((JButton)features[i]).setText("Bin   "+ Long.toBinaryString(num).toUpperCase());
				}
		
			} 
		}
	
		
	}
    private void replaceOp(String op) {
        if (proceedOp == null) {
            proceedOp = new Operation(textOnScreen, op, null);
        } else {
            proceedOp = new Operation(textOnScreen, op, null);
        }
    }

    private String eval(String par1, String op, String par2) {
        String result = "";
        double r = 0, op1 = (Double.parseDouble(par1)), op2 = Double.parseDouble(par2);
        if (op.equals("+")) {
            r = op1 + op2;
        }
        if (op.equals("*")) {
            r = op1 * op2;
            System.out.println(r);
        }
        if (op.equals("-")) {
            r = op1 - op2;
        }
        if (op.equals("/")) {
            if (op2 == 0 && op1 == 0) {
                result = "Result of function is undefined";
            } else if (op2 == 0) {
                result = "Cannot divide by zero";
            } else {
                r = op1 / op2;
            }
        }
		if(op.equals("Mod"))
		{
			r = op1 % op2;
		}
        if (result.equals("")) {
            result += r;
        }
        char c = ' ';
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == 'E') {
                c = 'E';
            }
        }
        if (c != 'E') {
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == '.' && Double.parseDouble(result.substring(i + 1, result.length())) == 0) {
                    result = result.substring(0, i + 1);
                    i = result.length();
                }
            }
        }
        return result;
    }

    private String eval(String par1, String op) {
        String result = "";
        double r = 0, op1 = (Double.parseDouble(par1));
        if (op.equals("+/-")) {
            r = op1 - 2 * op1;
        }
        if (op.equals("1/x")) {
            if (op1 == 0) {
                result = "Cannot divide by zero";
            } else {
                r = 1 / op1;
            }
        }
        if (op.equals("sqrt")) {
            if (op1 < 0) {
                result = "invalid input for function";
            } else {
                r = Math.sqrt(op1);
            }
        }
        if (result.equals("")) {
            result += r;
        }
        char c = ' ';
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == 'E') {
                c = 'E';
            }
        }
        if (c != 'E') {
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == '.' && Double.parseDouble(result.substring(i + 1, result.length())) == 0) {
                    result = result.substring(0, i);
                    i = result.length();
                }
            }
        }
        return result;
    }

    private void manageDigits(String digit) {
        if (textOnScreen.length() < 33) {
            if (concDigit) {
                if (currCharIsCom) {
                    textOnScreen += ".";
                }
                textOnScreen += digit;
                if (Double.parseDouble(textOnScreen) == 0 && !isDouble) {
                    textOnScreen = "0";
                    concDigit = false;
                }
            } else {
                textOnScreen = digit;
                if (Double.parseDouble(textOnScreen) != 0) {
                    concDigit = true;
                }
            }
            if (proceedOp != null) {
                if (!("=").equals(proceedOp.lastOp)) {
                    proceedOp.param2 = textOnScreen;
                }
            }
            affiche(textOnScreen);
            currCharIsCom = false;
        } else if (proceedOp != null) {
            if (proceedOp.param2 == null) {
                textOnScreen = digit;
                manageDigits(digit);
            }
        }
    }

    private void equal() {
        if (proceedOp != null) {
            if (proceedOp.param2 == null) {
                proceedOp.param2 = textOnScreen;
            }
            proceedOp.lastOp = "=";
            textOnScreen = proceedOp.param1 = eval(proceedOp.param1, proceedOp.currentOp, proceedOp.param2);
        }
        affiche(textOnScreen);
        concDigit = false;
        currCharIsCom = false;
        isDouble = false;
    }

    private String updateComma(String toReturn) {
        for (int i = 0; i < toReturn.length(); i++) {
            if (toReturn.charAt(i) == ',') {
                toReturn = toReturn.substring(0, i) + "." + toReturn.substring(i + 1, toReturn.length());
                i = toReturn.length();
            }
            if (i < toReturn.length()) {
                if (toReturn.charAt(i) == '.' && i < toReturn.length()) {
                    toReturn = toReturn.substring(0, i) + "," + toReturn.substring(i + 1, toReturn.length());
                    i = toReturn.length();
                }
            }
        }
        return toReturn;
    }

    /**
    * main
    */
    public static void main(String[] argv) {
        new Calculator();
    }
}