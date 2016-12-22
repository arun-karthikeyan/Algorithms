public class BinarySearch<E extends Comparable<E>> {

	/**
	 * Binary Search to find the first occurrence of the key in the given array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param key
	 *            the key which has to be searched for
	 * @return pos if key exists or -1 if key doesn't exist
	 */
	public int firstOccurrenceBinarySearch(E[] array, E key) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			if (key.compareTo(array[mid]) <= 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return low < len && array[low].compareTo(key) == 0 ? low : -1;
	}

	/**
	 * Binary Search to find the last occurrence of they key in the given array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param key
	 *            the key which has to be searched for
	 * @return pos if key exists or -1 if key doesn't exist
	 */
	public int lastOccurrenceBinarySearch(E[] array, E key) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			if (key.compareTo(array[mid]) < 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return high > -1 && array[high].compareTo(key) == 0 ? high : -1;
	}

	/**
	 * Binary Search to find if the key is present in the given sorted array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param key
	 *            the key which has to be searched for
	 * @return pos if key exists or -1 if the key doesn't exist in this array
	 */
	public int binarySearch(E[] array, E key) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			int compare = key.compareTo(array[mid]);
			if (compare < 0) {
				high = mid - 1;
			} else if (compare > 0) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	/**
	 * Binary search to find the first position where the given key can be
	 * inserted in the sorted array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param key
	 *            the key which has to be inserted
	 * @return first possible insert position of key in the given array
	 */
	public int firstInsertPosition(E[] array, E key) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			;
			if (key.compareTo(array[mid]) <= 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

	/**
	 * Binary search to find the last position where the given key can be
	 * inserted in the sorted array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param key
	 *            the key which has to be inserted
	 * @return last possible insert position of key in the given array
	 */
	public int lastInsertPosition(E[] array, E key) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			if (key.compareTo(array[mid]) < 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

	public static void main(String[] args) {
		BinarySearch<Integer> bs = new BinarySearch<Integer>();
		Integer[] array = new Integer[] { 1, 2, 3, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 10, 11, 12, 13, 13, 13,
				1000 };
		Integer val = 13;
		System.out.println("First occurrence of " + val + " is " + bs.firstOccurrenceBinarySearch(array, val));
		System.out.println("Last occurrence of " + val + " is " + bs.lastOccurrenceBinarySearch(array, val));
		System.out.println("First insert position of " + val + " is " + bs.firstInsertPosition(array, val));
		System.out.println("Last insert position of " + val + " is " + bs.lastInsertPosition(array, val));
		System.out.println("Is " + val + " present in the array ? " + (bs.binarySearch(array, val) > 0));
	}

}
