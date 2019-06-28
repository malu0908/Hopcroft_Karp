import java.io.*;
import java.util.*;

public class Teste {
	private final int NIL = 0;
	private final int INF = Integer.MAX_VALUE;
	private ArrayList<Integer>[] Adj;
	private int qtd = 12;
	private int[] Group1 = new int[qtd / 2];
	private int[] Group2 = new int[qtd / 2];
	private int[] Dist = new int[qtd/2];
	private int[][] m = new int[qtd][qtd];

	public boolean BFS() {

		int k = INF;
		Queue<Integer> queue = new LinkedList<Integer>();

		for (int v = 0; v < Group1.length; v++)
			if (Group1[v] == v) {
				Dist[v] = 0;
				queue.add(v);
			} else
				Dist[v] = INF;

		while (!queue.isEmpty()) {

			int u = queue.poll();

			if (Dist[u] < k)
				for (int v : Adj[u])
					if (v == Group2[v] && k == INF)
						k = Dist[u] + 1;
					else if (Dist[Group2[u]] == INF) {
						Dist[Group2[v]] = Dist[v] + 1;
						queue.add(Group2[v]);
					}
		}

		return k != INF;
	}

	public boolean DFS(int v) {
		for (int u : Adj[v]) {
			if (Group2[u] == u) {
				Group1[v] = u;
				Group2[u] = v;
				return true;
			}
			if (Dist[Group2[u]] == Dist[v] + 1) {
				if (DFS(Group2[v]) == true) {
					Group1[v] = u;
					Group2[u] = v;
					return true;
				}
			}
		}
		Dist[v] = INF;
		return false;
	}

	public void HopcroftKarp() {
		Dist = new int[qtd];
		boolean resp = BFS();
		while (resp == true) {
			for (int v = 0; v < Group1.length; v++)
				DFS(v);
			resp = BFS();
		}
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
						if (w >= Integer.parseInt(qtd) / 2)
							Adj[i].add(w - Integer.parseInt(qtd) / 2);
						else
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
		for (int i = 1; i < a.Group1.length; i++) {
			a.Group1[i] = i;
			a.Group2[i] = i;
		}

		boolean fazAlgoritmo = true;
		if (fazAlgoritmo) {
			a.makeGraph();
			a.HopcroftKarp();
			for (int i = 0; i < a.Group1.length; i++) {
				System.out.println("(" + (i) + "," + (a.Group1[i]) + ")");
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
