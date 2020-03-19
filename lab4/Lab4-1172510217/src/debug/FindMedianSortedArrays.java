package debug;

/**
 * Given two ordered integer arrays nums1 and nums2, with size m and n
 * Find out the median (double) of the two arrays.
 * You may suppose nums1 and nums2 cannot be null at the same time.
 *
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The output would be 2.0
 * 
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The output would be 2.5
 * 
 * Example 3:
 * nums1 = [1, 1, 1]
 * nums2 = [5, 6, 7]
 * The output would be 3.0
 * 
 * Example 4:
 * nums1 = [1, 1]
 * nums2 = [1, 2, 3]
 * The output would be 1.0
 */

public class FindMedianSortedArrays {

    public double findMedianSortedArrays(int[] A, int[] B) {
        /*----->>>此处修改<<<------*/
        /* 处理A为空，B不为空的情况 */
        if (A == null && B != null) {
            int n = B.length;
            return (B[n / 2] + B[(n - 1) / 2]) / 2.0;
        }
        /*----->>>此处修改<<<------*/
        /* 处理B为空，A不为空的情况 */
        if (B == null && A != null) {
            int n = A.length;
            return (A[n / 2] + A[(n - 1) / 2]) / 2.0;
        }
        int m = A.length;
        int n = B.length;
        if (m > n) {// 数组A存放小数组，B存放大数组
            int[] temp = A;
            A = B;
            B = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax + 1) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1;
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1;
            } else {
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = B[j - 1];
                } else if (j == 0) {
                    maxLeft = A[i - 1];
                } else {
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                int minRight = 0;
                if (i == m) {
                    minRight = B[j];
                } else if (j == n) {
                    minRight = A[i];
                } else {
                    minRight = Math.min(B[j], A[i]);
                }
                /*----->>>此处修改<<<------*/
                /*
                 * 当数组A和B长度和为奇数时，直接返回得到的minRight，而不是返回中位数前面的一个数字
                 */
                if ((m + n) % 2 == 1) {
                    return minRight;
                }
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

}
