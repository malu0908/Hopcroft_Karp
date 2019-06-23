package Emparelhamento1_Maximo;

import java.io.*;
import java.util.*;

public class Teste {
	private final int NIL = 0;
	private final int INF = Integer.MAX_VALUE;
	private ArrayList<Integer>[] Adj;
	private int[] Group;
	private int[] Dist;
	private int qtd = 6;
	private boolean[] notFree = new boolean[qtd];

	public boolean BFS() {
		// Cria uma fila
		Queue<Integer> queue = new LinkedList<Integer>();
		for (int v = 1; v < qtd; v++)
			// se eh igual a nil ele eh um vertice livre e eh add na fila
			if (Group[v] == NIL) {
				Dist[v] = 0;
				queue.add(v);
			} else
				Dist[v] = INF;

		Dist[NIL] = INF;

		// ele so testa caminhos de aumento minimos logo para que ele faca o
		// procedimento
		// a distancia do v tem de ser menor que o do NIL
		while (!queue.isEmpty()) {
			int v = queue.poll();
			if (Dist[v] < Dist[NIL])
				for (int u : Adj[v])
					if (Dist[Group[u]] == INF) {
						Dist[Group[u]] = Dist[v] + 1;
						queue.add(Group[u]);
					}
		}
		return Dist[NIL] != INF;
	}

	public boolean DFS(int v) {
		if (v != NIL) {
			for (int u : Adj[v])
				if (Dist[Group[u]] == Dist[v] + 1 && notFree[u] == false && notFree[v] == false)
					if (DFS(Group[u])) {
						Group[u] = v;
						Group[v] = u;

						notFree[u] = true;
						notFree[v] = true;
						return true;
					}

			Dist[v] = INF;
			return false;
		}
		return true;
	}

	public void HopcroftKarp() {
		Group = new int[qtd];
		Dist = new int[qtd];
		while (BFS())
			for (int v = 1; v < qtd; v++)
				if (Group[v] == NIL)
					DFS(v);
	}

	public void makeGraph() throws IOException {
		if (Adj == null) {
			FileReader arq = new FileReader(
					"C:\\Users\\maluf\\Desktop\\Bagunça\\Trabalho AEDS III\\src\\Emparelhamento1_Maximo\\input.txt");

			BufferedReader lerArq = new BufferedReader(arq);
			String qtd = lerArq.readLine();

			Adj = new ArrayList[Integer.parseInt(qtd)];

			String linha = lerArq.readLine();
			int i = 0;
			while (linha != null) {
				Adj[i] = new ArrayList<Integer>();
				String[] info = linha.split(",");
				for (int w = 0; w < Integer.parseInt(qtd); w++) {
					if (Integer.parseInt(info[w]) == 1)
						Adj[i].add(w);
				}
				linha = lerArq.readLine();
				i++;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Teste a = new Teste();
		a.makeGraph();
		a.HopcroftKarp();
		for (int i = 0; i < a.qtd; i++) {
			System.out.println("(" + i + "," + a.Group[i] + ")");
		}
	}
}
