package model;

import java.awt.Color;

public class Path {
	House[][] MatrizCaminhoCompleto = new House[15][15];
	House[] GreenCaminhoColorido = new House[59];
	House[] RedCaminhoColorido = new House[59];
	House[] BlueCaminhoColorido = new House[59];
	House[] YellowCaminhoColorido = new House[59];
	
	private static Path pfirstInstance = null;
	
	public static Path getInstance(){
		if(pfirstInstance == null){
			
			pfirstInstance = new Path();
		}
		
		return pfirstInstance;
	}
	
	
	private Path(){
		this.MatrizCaminhoCompleto = CriaMatrizCaminhoCompleto(this.MatrizCaminhoCompleto);
		this.GreenCaminhoColorido = inicializaArrayCaminhoCor(Color.green, this.MatrizCaminhoCompleto);
		this.RedCaminhoColorido = inicializaArrayCaminhoCor(Color.red, this.MatrizCaminhoCompleto);
		this.BlueCaminhoColorido = inicializaArrayCaminhoCor(Color.blue, this.MatrizCaminhoCompleto);
		this.YellowCaminhoColorido = inicializaArrayCaminhoCor(Color.yellow, this.MatrizCaminhoCompleto);
	}
	
	public House[][] getCaminhoCompleto(){
		
		return this.MatrizCaminhoCompleto;
		
	}
	
	public House[] getGreen(){
		return this.GreenCaminhoColorido;
	}

	public House[] getRed(){
		return this.RedCaminhoColorido;
	}
	
	public House[] getBlue(){
		return this.BlueCaminhoColorido;
	}
	
	public House[] getYellow(){
		return this.YellowCaminhoColorido;
	}
	
	private House[][] CriaMatrizCaminhoCompleto(House[][] MatrizCaminhoCompleto){
		for(int coluna = 0; coluna < 15; coluna++){
			for(int linha = 0; linha < 15; linha++){
				MatrizCaminhoCompleto[linha][coluna] = new House(40*linha, 40*coluna);
			}
		}

		return MatrizCaminhoCompleto;

	}
	
	private House[] inicializaArrayCaminhoCor(Color color, House[][] MatrizCaminhoCompleto){

		final House[] ArrayCaminhoCor = new House[59];

		if(color == Color.green){
			for(int i = 0; i < 59; i++){
				if(i < 5){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[i+1][6];
				}
				else if(i >= 5 && i < 11){
					ArrayCaminhoCor[i] =  MatrizCaminhoCompleto[6][10-i];
				}
				else if(i >= 11 && i < 13){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[i-4][0];
				}
				else if(i >= 13 && i < 18){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[8][i-12];
				}
				else if(i >= 18 && i < 24){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[i-9][6];
				}
				else if(i >= 24 && i < 26){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[14][i-17];
				}
				else if(i >= 26 && i < 31){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[39-i][8];
				}
				else if(i >= 31 && i < 37){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[8][i-22];
				}
				else if(i >= 37 && i < 39){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[44-i][14];
				}
				else if(i >= 39 && i < 44){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[6][52-i];
				}
				else if(i >= 44 && i < 50){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[49-i][8];
				}
				else if(i >= 50 && i < 52){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[0][57-i];
				}
				else if(i == 52){
					ArrayCaminhoCor[i] = ArrayCaminhoCor[0];

				}
				else if(i >= 53 && i < 59){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[i-52][7];
				}
			}
		}


		else if(color == Color.blue){

			for(int i = 0; i < 59; i++){
				if(i < 5){
					ArrayCaminhoCor[i+26] = MatrizCaminhoCompleto[i+1][6];
				}
				else if(i >= 5 && i < 11){
					ArrayCaminhoCor[i+26] = MatrizCaminhoCompleto[6][10-i];
				}
				else if(i >= 11 && i < 13){
					ArrayCaminhoCor[i+26] = MatrizCaminhoCompleto[i-4][0];
				}
				else if(i >= 13 && i < 18){
					ArrayCaminhoCor[i+26] = MatrizCaminhoCompleto[8][i-12];
				}
				else if(i >= 18 && i < 24){
					ArrayCaminhoCor[i+26] = MatrizCaminhoCompleto[i-9][6];
				}
				else if(i >= 24 && i < 26){
					ArrayCaminhoCor[i+26] = MatrizCaminhoCompleto[14][i-17];
				}
				else if(i >= 26 && i < 31){
					ArrayCaminhoCor[i-26] = MatrizCaminhoCompleto[39-i][8];
				}
				else if(i >= 31 && i < 37){
					ArrayCaminhoCor[i-26] = MatrizCaminhoCompleto[8][i-22];
				}
				else if(i >= 37 && i < 39){
					ArrayCaminhoCor[i-26] = MatrizCaminhoCompleto[44-i][14];
				}
				else if(i >= 39 && i < 44){
					ArrayCaminhoCor[i-26] =  MatrizCaminhoCompleto[6][52-i];
				}
				else if(i >= 44 && i < 50){
					ArrayCaminhoCor[i-26] = MatrizCaminhoCompleto[49-i][8];
				}
				else if(i >= 50 && i < 52){
					ArrayCaminhoCor[i-26] = MatrizCaminhoCompleto[0][57-i];
				}
				else if(i == 52){
					ArrayCaminhoCor[i] = ArrayCaminhoCor[0];
				}
				else if(i >= 53 && i < 59){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[66-i][7];
				}
			}
		}


		else if(color == Color.red){
			for(int i = 0; i < 59; i++){
				if(i < 5){
					ArrayCaminhoCor[i+39] = MatrizCaminhoCompleto[i+1][6];
				}
				else if(i >= 5 && i < 11){
					ArrayCaminhoCor[i+39] = MatrizCaminhoCompleto[6][10-i];
				}
				else if(i >= 11 && i < 13){
					ArrayCaminhoCor[i+39] = MatrizCaminhoCompleto[i-4][0];
				}
				else if(i >= 13 && i < 18){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[8][i-12];
				}
				else if(i >= 18 && i < 24){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[i-9][6];
				}
				else if(i >= 24 && i < 26){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[14][i-17];
				}
				else if(i >= 26 && i < 31){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[39-i][8];
				}
				else if(i >= 31 && i < 37){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[8][i-22];
				}
				else if(i >= 37 && i < 39){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[44-i][14];
				}
				else if(i >= 39 && i < 44){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[6][52-i];
				}
				else if(i >= 44 && i < 50){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[49-i][8];
				}
				else if(i >= 50 && i < 52){
					ArrayCaminhoCor[i-13] = MatrizCaminhoCompleto[0][57-i];
				}
				else if(i == 52){
					ArrayCaminhoCor[i] = ArrayCaminhoCor[0];
				}
				else if(i >= 53 && i < 59){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[7][i-52];
				}
			}

		}

		else if(color == Color.yellow){

			for(int i = 0; i < 59; i++){
				if(i < 5){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[i+1][6];
				}
				else if(i >= 5 && i < 11){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[6][10-i];
				}
				else if(i >= 11 && i < 13){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[i-4][0];
				}
				else if(i >= 13 && i < 18){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[8][i-12];
				}
				else if(i >= 18 && i < 24){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[i-9][6];
				}
				else if(i >= 24 && i < 26){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[14][i-17];
				}
				else if(i >= 26 && i < 31){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[39-i][8];
				}
				else if(i >= 31 && i < 37){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[8][i-22];
				}
				else if(i >= 37 && i < 39){
					ArrayCaminhoCor[i+13] = MatrizCaminhoCompleto[44-i][14];
				}
				else if(i >= 39 && i < 44){
					ArrayCaminhoCor[i-39] = MatrizCaminhoCompleto[6][52-i];
				}
				else if(i >= 44 && i < 50){
					ArrayCaminhoCor[i-39] = MatrizCaminhoCompleto[49-i][8];
				}
				else if(i >= 50 && i < 52){
					ArrayCaminhoCor[i-39] = MatrizCaminhoCompleto[0][57-i];
				}
				else if(i == 52){
					ArrayCaminhoCor[i] = ArrayCaminhoCor[0];

				}
				else if(i >= 53 && i < 59){
					ArrayCaminhoCor[i] = MatrizCaminhoCompleto[7][66-i];

				}
			}

		}

		return ArrayCaminhoCor;
	}

}
