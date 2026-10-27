void main(String[] args) {
    int maxFlow = FordFulkerson.FordFulkersonAlgorithm(6,new int[][]{{1, 2, 10}, {1, 3, 10}, {2,3,2},{2, 5, 8},{3,5,9},{2,4,4},{5,4,6},{4,6,10},{5,6,10}});
    System.out.println("Maxflow = " + maxFlow);
}