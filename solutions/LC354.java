import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

record Envelope(int width, int height) {}

// class Envelope {
//     public int w;
//     public int h;

//     public Envelope(int w, int h) {
//         this.w = w;
//         this.h = h;
//     }

//     public int getWidth() {
//         return w;
//     }

//     public int getHeight() {
//         return h;
//     }
// }

class Solution {

    // public static <T, U> Predicate<T> distinctByKey(Function<? super T, ? extends U> keyExtractor) {
    //     Set<Object> seen = new HashSet<>();
    //     return t -> seen.add(keyExtractor.apply(t));
    // }

    // public static Predicate<Envelope> distinctByWidth() {
    //     Set<Integer> seen = new HashSet<>();
    //     return env -> seen.add(env.getWidth());
    // }

    private int findLongestIncreasingSubsequence(List<Integer> nums) {
        List<Integer> seq = new ArrayList<>();

        for (var num : nums) {
            // num = -num;
            if (seq.isEmpty() || num > seq.get(seq.size() - 1)) {
                seq.add(num);
            } else {
                int insertPoint = Collections.binarySearch(seq, num);
                if (insertPoint < 0) {
                    insertPoint = -(insertPoint + 1);
                }

                seq.set(insertPoint, num);
            }
            // System.out.println(seq);
        }

        return seq.size();
    }

    // private int getMaxEnvolopesBy(List<Envelope> envelopes, Function<Envelope, Integer> sorter,
    //         Function<Envelope, Integer> selector) {
    //     var sortedEnvelopes = envelopes.stream().sorted(Comparator.comparing(sorter).reversed().thenComparing(selector).reversed())
    //             .toList();

    //     List<Integer> vals = new ArrayList<>();
    //     for (var envelope : sortedEnvelopes) {
    //         // System.out.println(envelope.getWidth() + " " + envelope.getHeight());
    //         vals.add(selector.apply(envelope));
    //     }

    //     // System.out.println(vals);
    //     return findLongestIncreasingSubsequence(vals);
    // }

    public int maxEnvelopes(int[][] E) {
        // var envelopes = IntStream.range(0, E.length)
        //         .mapToObj(i -> new Envelope(E[i][0], E[i][1]))
        //         .sorted(
        //                 Comparator.comparing(Envelope::getWidth)
        //                         .thenComparing(Envelope::getHeight)
        //                         .reversed())
        //         .toList();

        // List<Integer> heights = new ArrayList<>();

        // int lastWidth = 0;
        // int lastHeight = 0;
        // for (var envelope : envelopes) {
        //     System.out.println(envelope.getWidth() + " " + envelope.getHeight());
        //     if (envelope.getWidth() != lastWidth && envelope.getHeight() != lastHeight) {
        //         heights.add(envelope.getHeight());
        //         lastWidth = envelope.getWidth();
        //         lastHeight = envelope.getHeight();
        //     }
        // }

        var heights = IntStream.range(0, E.length)
                .mapToObj(i -> new Envelope(E[i][0], E[i][1]))
                .sorted(
                        Comparator.comparing(Envelope::width)
                                .reversed()
                                .thenComparing(Envelope::height)
                                .reversed())
                .map(Envelope::height)
                .toList();

        return findLongestIncreasingSubsequence(heights);

        // var envelopes = IntStream.range(0, E.length)
        //         .mapToObj(i -> new Envelope(E[i][0], E[i][1])).toList();
        // return Math.max(getMaxEnvolopesBy(envelopes, Envelope::getWidth, Envelope::getHeight),
        //         getMaxEnvolopesBy(envelopes, Envelope::getHeight, Envelope::getWidth));
    }
}