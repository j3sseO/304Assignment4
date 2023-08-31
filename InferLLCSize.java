import java.util.*;

// Name: Jesse O'Connor
// ID: 1534760

/**
 * Entry class
 */
public class InferLLCSize {

    public static void main(String[] args) {
            System.out.println("LLC Cache Analyzer:");

            InferCache llc = new InferCache();
            llc.measure();
        }
}

/**
 * Class that holds source code to infer the size of the LLC
 */
class InferCache {

    final int KB = 1024;
    final int runNum = 50;

    // Array that holds increasing exponential sizes of arrays
    int [] arraySizes = {1 * KB, 2 * KB, 4 * KB, 8 * KB, 16 * KB, 32 * KB,
                        64 * KB, 128 * KB, 256 * KB, 512 * KB, 1024 * KB,
                        2048 * KB, 4096 * KB, 8192 * KB, 16384 * KB,
                        32768 * KB, 65536 * KB, 131072 * KB, 262144 * KB};

    /**
     * Measure access time of arrays, access time will increase significantly,
     * when array not longer fits in cache and has to be read from main
     * memory
     */
    public void measure() {

        // Unmotivated large number of steps
        int steps = 64 * 128 * 256 * 32; 

        long startTime, averageTime, endTime;
        int lengthMod;

        System.out.println("Results (ns):");

        // Loop that creates byte arrays for each of the sizes specified
        for (int i = 0; i < arraySizes.length; i++) {
            averageTime = 0;
            byte[] array = new byte[arraySizes[i]];

            // Loop through 50 times for each array size
            for (int j = 0; j <= runNum; j++) {
                startTime = System.nanoTime(); // Starts timer
                lengthMod = array.length - 1;
                // Tries to access and modify random array index
                for (int k = 0; k < steps; k++) {
                    array[(k * 64) & lengthMod]++;
                }
                endTime = System.nanoTime(); // Ends timer
                averageTime += (endTime - startTime); // Calculates average time for each loop
            }

            System.out.println((arraySizes[i] / KB) + "kB, " + (averageTime / runNum));
        }
    }
}