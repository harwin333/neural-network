import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class NeuralNetwork {
    private int layers;
    private int[] nodes;
    private Map<Edge, Double> weights;

    public NeuralNetwork(int layers, int[] nodes) {
        this.layers = layers;
        this.nodes = nodes;
        this.weights = new HashMap<>();
        initializeWeights();
    }

    private void initializeWeights() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < layers - 1; i++) {
            for (int node1 = 0; node1 < nodes[i]; node1++) {
                for (int node2 = 0; node2 < nodes[i + 1]; node2++) {
                    double weight = readWeight(scanner, i, node1, i + 1, node2);
                    weights.put(new Edge(i, node1, i + 1, node2), weight);
                }
            }
        }
    }

    private double readWeight(Scanner scanner, int layerFrom, int nodeFrom, int layerTo, int nodeTo) {
        while (true) {
            try {
                System.out.print("Enter weight for connection (" + layerFrom + ", " + nodeFrom + ") to (" + layerTo + ", " + nodeTo + "): ");
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid weight.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    public double getConnectionWeight(int layerFrom, int nodeFrom, int layerTo, int nodeTo) {
        Edge edge = new Edge(layerFrom, nodeFrom, layerTo, nodeTo);
        return weights.getOrDefault(edge, 0.0);
    }
}

class Edge {
    private int layerFrom, nodeFrom, layerTo, nodeTo;

    public Edge(int layerFrom, int nodeFrom, int layerTo, int nodeTo) {
        this.layerFrom = layerFrom;
        this.nodeFrom = nodeFrom;
        this.layerTo = layerTo;
        this.nodeTo = nodeTo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + layerFrom;
        result = prime * result + nodeFrom;
        result = prime * result + layerTo;
        result = prime * result + nodeTo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        if (layerFrom != other.layerFrom)
            return false;
        if (nodeFrom != other.nodeFrom)
            return false;
        if (layerTo != other.layerTo)
            return false;
        if (nodeTo != other.nodeTo)
            return false;
        return true;
    }
}

public class NeuralNetworkMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of layers: ");
        int layers = scanner.nextInt();

        int[] nodes = new int[layers];
        for (int i = 0; i < layers; i++) {
            System.out.print("Enter the number of nodes in layer " + i + ": ");
            nodes[i] = scanner.nextInt();
        }

        NeuralNetwork neuralNetwork = new NeuralNetwork(layers, nodes);

        System.out.print("Enter the layer from: ");
        int layerFrom = scanner.nextInt();
        System.out.print("Enter the node from: ");
        int nodeFrom = scanner.nextInt();
        System.out.print("Enter the layer to: ");
        int layerTo = scanner.nextInt();
        System.out.print("Enter the node to: ");
        int nodeTo = scanner.nextInt();

        double weight = neuralNetwork.getConnectionWeight(layerFrom, nodeFrom, layerTo, nodeTo);
        System.out.println("Weight for connection (" + layerFrom + ", " + nodeFrom + ") to (" + layerTo + ", " + nodeTo + "): " + weight);
    }
}
