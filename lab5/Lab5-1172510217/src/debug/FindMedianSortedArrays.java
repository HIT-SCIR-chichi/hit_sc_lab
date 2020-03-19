package debug;

/**
 * Given two ordered integer arrays nums1 and nums2, with size m and n
 * Find out the median (double) of the two arrays.
 * You may suppose nums1 and nums2 cannot be null at the same time.
 * 
 * <p>Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The output would be 2.0
 * 
 * <p>Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The output would be 2.5
 * 
 * <p>Example 3:
 * nums1 = [1, 1, 1]
 * nums2 = [5, 6, 7]
 * The output would be 3.0
 * 
 * <p>Example 4:
 * nums1 = [1, 1]
 * nums2 = [1, 2, 3]
 * The output would be 1.0
 */

public class FindMedianSortedArrays {

  /**
   * 找到两个有序数组的中位数.
   * 
   * @param  a 有序数组a
   * @param  b 有序数组b
   * @return   返回中位数
   */
  public double findMedianSortedArrays(int[] a, int[] b) {
    /*----->>>此处修改<<<------*/
    /* 处理A为空，B不为空的情况 */
    if (a == null && b != null) {
      int n = b.length;
      return (b[n / 2] + b[(n - 1) / 2]) / 2.0;
    }
    /*----->>>此处修改<<<------*/
    /* 处理B为空，A不为空的情况 */
    if (b == null && a != null) {
      int n = a.length;
      return (a[n / 2] + a[(n - 1) / 2]) / 2.0;
    }
    int m = a.length;
    int n = b.length;
    if (m > n) { // 数组A存放小数组，B存放大数组
      int[] temp = a;
      a = b;
      b = temp;
      int tmp = m;
      m = n;
      n = tmp;
    }
    int imin = 0;
    int imax = m;
    int halfLen = (m + n) / 2;
    while (imin <= imax) {
      int i = (imin + imax + 1) / 2;
      int j = halfLen - i;
      if (i < imax && b[j - 1] > a[i]) {
        imin = i + 1;
      } else if (i > imin && a[i - 1] > b[j]) {
        imax = i - 1;
      } else {
        int maxLeft = 0;
        if (i == 0) {
          maxLeft = b[j - 1];
        } else if (j == 0) {
          maxLeft = a[i - 1];
        } else {
          maxLeft = Math.max(a[i - 1], b[j - 1]);
        }
        int minRight = 0;
        if (i == m) {
          minRight = b[j];
        } else if (j == n) {
          minRight = a[i];
        } else {
          minRight = Math.min(b[j], a[i]);
        }
        /*----->>>此处修改<<<------*/
        /*
         * 当数组A和B长度和为奇数时，直接返回得到的minRight，而不是返回中位数前面的一个数字
         */
        if ((m + n) % 2 != 0) {
          return minRight;
        }
        return (maxLeft + minRight) / 2.0;
      }
    }
    return 0.0;
  }

}
