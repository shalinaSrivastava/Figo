package com.Ecomaxgo.maxpay.kiosk_status;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.content.res.Resources;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.util.SparseArray;
//import android.view.ViewGroup;
//
//public class TabsPagerAdapter
//
//   extends FragmentPagerAdapter {
//
//    private static Object LeaveRequest = new LeaveRequest();
//    private static Object Attendance = new Attendance();
//    private final Resources resources;
//
//        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
//
//        /**
//         * Create pager adapter
//         *
//         * @param resources
//         * @param fm
//         */
//    public TabsPagerAdapter(final Resources resources, FragmentManager fm) {
//            super(fm);
//            this.resources = resources;
//        }
//
//        @Override
//        public android.support.v4.app.Fragment getItem(int position) {
//            final Fragment result = null;
//            switch (position) {
//                case 0:
//                    // First Fragment of First Tab
//                    Attendance = new Attendance();
//                    break;
//                case 1:
//                    // First Fragment of Second Tab
//                    LeaveRequest = new LeaveRequest();
//                    break;
//
//                default:
//                    Attendance = new Attendance();
//                    break;
//            }
//
//            return result;
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
//        public CharSequence getPageTitle(final int position) {
//            switch (position) {
//                case 0:
//                    return resources.getString(R.string.page_1);
//                case 1:
//                    return resources.getString(R.string.page_2);
//                case 2:
//                    return resources.getString(R.string.page_3);
//                default:
//                    return null;
//            }
//        }
//
//        /**
//         * On each Fragment instantiation we are saving the reference of that Fragment in a Map
//         * It will help us to retrieve the Fragment by position
//         *
//         * @param container
//         * @param position
//         * @return
//         */
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            Fragment fragment = (Fragment) super.instantiateItem(container, position);
//            registeredFragments.put(position, fragment);
//            return fragment;
//        }
//
//        /**
//         * Remove the saved reference from our Map on the Fragment destroy
//         *
//         * @param container
//         * @param position
//         * @param object
//         */
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            registeredFragments.remove(position);
//            super.destroyItem(container, position, object);
//        }
//
//
//        /**
//         * Get the Fragment by position
//         *
//         * @param position tab position of the fragment
//         * @return
//         */
//        public Fragment getRegisteredFragment(int position) {
//            return registeredFragments.get(position);
//        }
//}
