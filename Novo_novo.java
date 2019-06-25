import java.io.*;
import java.util.*;

public class Teste {
	private final int NIL = 0;
	private final int INF = Integer.MAX_VALUE;
	private ArrayList<Integer>[] Adj;
	private int qtd = 100;
	private int[] Group = new int[qtd];
	private int[] Dist = new int[qtd];
	private boolean[] notFree = new boolean[qtd];
	private int[][] m = new int[qtd][qtd];

	public boolean BFS(){

		Queue<Integer> queue = new LinkedList<Integer>();

		for (int v = 1; v < qtd; v++)
			if (Group[v] == NIL) {
				Dist[v] = 0;
				queue.add(v);
			}

			else
				Dist[v] = INF;

		Dist[NIL] = INF;

		while (!queue.isEmpty()){

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

						if (v < qtd / 2) {
							Group[v + (qtd /2)] = u - qtd/2;
							notFree[v + (qtd / 2)] = true;
							
							Group[u - (qtd/2)] = v + qtd/2;
							notFree[u - (qtd/2)] = true;
						}

						return true;
					}

			Dist[v] = INF;
			return false;
		}
		return true;
	}

	public void HopcroftKarp() {
		Dist = new int[qtd];
		while (BFS())
			for (int v = 1; v < qtd; v++)
				if (Group[v] == NIL)
					DFS(v);
	}

	public void makeGraph() throws IOException {
		if (Adj == null) {
			FileReader arq = new FileReader("/home/2018.1.08.015/Desktop/Hopcroft_Karp/src/input.txt");

			BufferedReader lerArq = new BufferedReader(arq);
			String qtd = lerArq.readLine();

			Adj = new ArrayList[Integer.parseInt(qtd)];

			String linha = lerArq.readLine();
			int i = 0;
			while (linha != null) {
				Adj[i] = new ArrayList<Integer>();
				String[] info = linha.split(",");
				for (int w = 0; w < Integer.parseInt(qtd); w++) {
					if (Integer.parseInt(info[w]) == 1) {
						Adj[i].add(w);						
					}
				}
				linha = lerArq.readLine();
				i++;
			}
			lerArq.close();
		}
	}

	public void montaMatriz() {
		Random r = new Random();

		for (int i = 0; i < qtd / 2; i++) {
			for (int j = qtd / 2; j < qtd; j++) {
				if ((i + qtd / 2) != j) {
					int a = r.nextInt(2);
					if (m[i][j] == 0) {
						m[i][j] = a;
						if (a == 1)
							m[j - qtd / 2][i + qtd / 2] = 1;
					}
				}
			}
		}

		for (int i = qtd / 2; i < qtd; i++) {
			for (int j = 0; j < qtd / 2; j++) {
				m[i][j] = m[j][i];
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Teste a = new Teste();
		boolean fazAlgoritmo = true;
		if (fazAlgoritmo) {
			a.makeGraph();
			a.HopcroftKarp();
			for (int i = 0; i < a.qtd; i++) {
				System.out.println("(" + (i) + "," + (a.Group[i]) + ")");
			}
		} else {
			a.montaMatriz();
			for (int i = 0; i < a.m.length; i++) {
				for (int j = 0; j < a.m[0].length; j++) {
					System.out.print(a.m[i][j]);
					if ((j + 1) != a.qtd)
						System.out.print(",");
				}
				System.out.println();
			}
		}
	}
}
