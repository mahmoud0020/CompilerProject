/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Mohamed Gamal
 */
public class Compiler_Scanner {
    
    
    public String line;
    int CurrentLineNumber = 1;
    int index = 0;
    int lexemeNumberInLine = 0;
    String word;

    public Compiler_Scanner() {
        
    }

    public enum Token {
        Class, Inheritance, Condition, Integer, SInteger, Character, String, Float,
        SFloat, Void, Break, Loop, Return, Struct, Switch, StartStatement, EndStatement,
        ArithmeticOperation, LogicOperator, RelationalOperator, AssignmentOperator,
        AccessOperator, Braces, Constant, QuotationMark, Inclusion, Comment, Delimiter, UnMatched, IDENTIFIER, EndOfLine
    }

    //this function take word from the GetToken function then compair it with the available tokens
    public Token CheckKeyword(String word) {
        switch (word) {
            case "Divisio":
                return Token.Class;
            case "InferedFrom":
                return Token.Inheritance;
            case "WhetherDo-Else":
                return Token.Condition;
            case "Ire":
                return Token.Integer;
            case "Sire":
                return Token.SInteger;
            case "Clo":
                return Token.Character;
            case "SetOfClo":
                return Token.String;
            case "FBU":
                return Token.Float;
            case "SFBU":
                return Token.SFloat;
            case "NoneValue":
                return Token.Void;
            case "TerminateThisNow":
                return Token.Break;
            case "RingWhen":
                return Token.Loop;
            case "BackedValue":
                return Token.Return;
            case "STT":
                return Token.Struct;
            case "Check-CaseOf":
                return Token.Switch;
            case "Beginning":
                return Token.StartStatement;
            case "End":
                return Token.EndStatement;
            case "Using":
                return Token.Inclusion;
            default:
                if ( index > word.length() && line.charAt(index - word.length()) == '@') {
                    return Token.IDENTIFIER;
                } else {
                    return Token.UnMatched;
                }
        }
    }

    public Token GetToken(String character) {

        while (line.length() > index && line.charAt(index) == ' ') {
            index++;
        }
        if (index == line.length()) {
            return Token.EndOfLine;
        }
        if (line.charAt(index) == '=') {
            word = "=";
            if (line.length() > index + 1 && line.charAt(index + 1) == '=') {
                index++;
                word = "==";
                return Token.RelationalOperator;
            }
            return Token.AssignmentOperator;
        } else if (line.charAt(index) == '+') {
            word = "+";
            return Token.ArithmeticOperation;
        } else if (line.charAt(index) == '<') {
            word = "<";
            if (line.length() > index + 1 && line.charAt(index + 1) == '=') {
                index++;
                word = "<=";
            }
            return Token.RelationalOperator;
        } else if (line.charAt(index) == '>') {
            word = ">";
            if (line.length() > index + 1 && line.charAt(index + 1) == '=') {
                index++;
                word = ">=";
            }
            return Token.RelationalOperator;
        } else if (line.charAt(index) == '-') {
            word = "-";
            return Token.ArithmeticOperation;
        } else if (line.length() > index + 1 && line.charAt(index) == '!' && line.charAt(index + 1) == '=') {
            word = "!=";
            index++;
            return Token.ArithmeticOperation;
        } else if (line.charAt(index) == '~') {
            word = "~";
            return Token.LogicOperator;
        } else if (line.length() > index + 1 && line.charAt(index) == '&' && line.charAt(index + 1) == '&') {
            word = "&&";
            index++;
            return Token.LogicOperator;
        } else if (line.length() > index + 1 && line.charAt(index) == '|' && line.charAt(index + 1) == '|') {
            word = "||";
            index++;
            return Token.LogicOperator;
        } else if (line.length() > index + 1 && line.charAt(index) == '/' && line.charAt(index + 1) == '#') {
            word = "/#";
            index++;
            return Token.Comment;
        } else if (line.length() > index + 1 && line.charAt(index) == '#' && line.charAt(index + 1) == '/') {
            word = "#/";
            index++;
            return Token.Comment;
        } else if (line.length() > index + 1 && line.charAt(index) == '/' && line.charAt(index + 1) == '-') {
            word = "/-";
            index++;
            return Token.Comment;
        } else if (line.charAt(index) == '*') {
            word = "*";
            return Token.ArithmeticOperation;
        } else if (line.charAt(index) == '/') {
            word = "/";
            return Token.ArithmeticOperation;
        } else if (line.charAt(index) == '\'') {
            word = "'";
            return Token.QuotationMark;
        } else if (line.charAt(index) == '"') {
            word = "\"";
            return Token.QuotationMark;
        } else if (line.charAt(index) == '.') {
            word = ".";
            return Token.AccessOperator;
        } else if (line.charAt(index) == '@') {
            word = "@";
            return Token.Delimiter;
        } else if (line.charAt(index) == ';') {
            word = ";";
            return Token.Delimiter;
        } else if (line.charAt(index) == '{') {
            word = "{";
            return Token.Braces;
        } else if (line.charAt(index) == '}') {
            word = "}";
            return Token.Braces;
        } else if (line.charAt(index) == '[') {
            word = "[";
            return Token.Braces;
        } else if (line.charAt(index) == ']') {
            word = "]";
            return Token.Braces;
        } else if (Character.isAlphabetic((int) line.charAt(index))) {//if a string starts with alphabet letter and not one of the sympols then 
            return AlphapetChecker();
        } else if (Character.isDigit(line.charAt(index))) {//if a word starts with digit then it must be a constant cant be a varible name and we get all digits next to it
            return DigitChecker();
        } else {
            word = line.charAt(index) + "";
            return Token.UnMatched;
        }
    }

    public Token AlphapetChecker() {
        word = "";
        while ((line.length() > index) && (line.charAt(index) != ' ') && (Character.isAlphabetic((int) line.charAt(index)) || (Character.isDigit(line.charAt(index))))) {
            word += line.charAt(index);
            index++;
        }
        index--;
        return CheckKeyword(word);
    }

    public Token DigitChecker() {
        word = "";
        while ((line.length() > index) && Character.isDigit(line.charAt(index))) {
            word += line.charAt(index);
            index++;
        }
        index--;
        return Token.Constant;
    }

    public void DisplayTokens(String file) throws IOException {
        Result re= new Result();
        BufferedReader br = new BufferedReader(new StringReader(file));
        index = 0;
        boolean matched;
        boolean BorderedSkip = false;
        Token tokenholder;
        while ((line = br.readLine()) != null) {
            while (index < line.length()) {
                tokenholder = GetToken(line);
                if (tokenholder == Token.Comment && word == "#/" && BorderedSkip) {
                    BorderedSkip = false;
                }
                if (!BorderedSkip) {
                    if (tokenholder != Token.EndOfLine) {
                        if (tokenholder == Token.UnMatched) {
                            matched = false;
                        } else {
                            matched = true;
                        }
                        lexemeNumberInLine++;
                        if (tokenholder == Token.Comment && word == "/#") {
                            BorderedSkip = true;
                        }
                        if (tokenholder == Token.Comment && word == "/-") {
                           
                            Result.AddRow(new Object[]{
                                                       CurrentLineNumber,
                                                       word,
                                                       tokenholder,
                                                       lexemeNumberInLine,
                                                       matched,
                                                       
                            });
                           // System.out.println("Line NO:" + CurrentLineNumber + " , Lexeme:" + word + " , Return Token:" + tokenholder + " , Lexeme NO in Line:" + lexemeNumberInLine + " , Matched:" + matched);
                            break;
                        }

                          Result.AddRow(new Object[]{
                                                       CurrentLineNumber,
                                                       word,
                                                       tokenholder,
                                                       lexemeNumberInLine,
                                                       matched,
                                                       
                            });
                        //System.out.println("Line NO:" + CurrentLineNumber + " , Lexeme:" + word + " , Return Token:" + tokenholder + " , Lexeme NO in Line:" + lexemeNumberInLine + " , Matched:" + matched);
                    }
                }
                index++;
            }
            CurrentLineNumber++;
            ClearLine();
        }
        
        re.setVisible(true);
    }

    public void ClearLine() {
        lexemeNumberInLine = 0;
        index = 0;
        lexemeNumberInLine = 0;
    }

}

