package com.library.navigator.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.io.Serializable;
import java.util.List;

public class FragmentNavigator {

    private final FragmentManager fragmentManager;
    private final int fragmentContainerId;

    private Bundle bundle;

    private String parcelableKey;
    private Parcelable parcelable;

    private String serializableKey;
    private Serializable serializable;

    private FragmentTransactionAnimations fragmentTransactionAnimations;

    private String clearBackStackTillTag;

    /**
     * Step : 1
     *
     * In case of activity :
     *
     * FragmentNavigator fragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), R.id.fragmentContainer);
     *
     * In case of fragment :
     *
     * FragmentNavigator fragmentNavigator = new FragmentNavigator(getChildFragmentManager(), R.id.childFragmentContainer);
     */
    public FragmentNavigator(FragmentManager fragmentManager, @IdRes int fragmentContainerId) {
        this.fragmentManager = fragmentManager;
        this.fragmentContainerId = fragmentContainerId;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    /**
     * Pass Bundle
     *
     * @param bundle
     */
    public FragmentNavigator setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    /**
     * Pass Serializable Model class
     *
     * @param key
     * @param serializable
     */
    public FragmentNavigator setSerializable(String key, Serializable serializable) {
        this.serializableKey = key;
        this.serializable = serializable;
        return this;
    }

    /**
     * Pass Parcelable Model class
     *
     * @param key
     * @param parcelable
     */
    public FragmentNavigator setParcelable(String key, Parcelable parcelable) {
        this.parcelableKey = key;
        this.parcelable = parcelable;
        return this;
    }

    /**
     * How to open and close fragment animations
     *
     * @param fragmentTransactionAnimations
     */
    public FragmentNavigator setAnimation(FragmentTransactionAnimations fragmentTransactionAnimations) {
        this.fragmentTransactionAnimations = fragmentTransactionAnimations;
        return this;
    }

    /**
     * Remove fragment from back stack till the given tag.
     *
     * @param fragmentClass example : clearBackStackTillTag(HomeFragment.class)
     */
    public FragmentNavigator clearBackStackTillTag(Class<? extends Fragment> fragmentClass) {
        this.clearBackStackTillTag = fragmentClass.getSimpleName();
        return this;
    }

    /**
     * Add fragments in frame layout.
     *
     * @param fragment       fragment instance
     * @param addToBackStack true : if you want to add in back stack
     *                       false : not add in back stack
     */
    private void add(Fragment fragment, boolean addToBackStack) {
        navigate(fragment, addToBackStack, TransactionBehavior.ADD);
    }

    /**
     * Replace fragments in frame layout.
     *
     * @param fragment       fragment instance
     * @param addToBackStack true : if you want to add in back stack
     *                       false : not add in back stack
     */
    private void replace(Fragment fragment, boolean addToBackStack) {
        navigate(fragment, addToBackStack, TransactionBehavior.REPLACE);
    }

    private boolean isFragmentAdded(final Fragment fragment) {
        return fragment != null && fragment.isAdded();
    }

    private void navigate(Fragment fragment, boolean addToBackStack, TransactionBehavior transactionBehavior) {
        Class<? extends Fragment> fragmentClass = fragment.getClass();
        String fragmentTag = fragmentClass.getSimpleName(); // we use it for fragment tag

        if (isFragmentAdded(fragment)) {
            System.out.println("fragment: " + fragmentTag + " is null or already added.");
        } else {
            if (bundle != null) {

                if (serializable != null) {
                    bundle.putSerializable(serializableKey, serializable);
                }

                if (parcelable != null) {
                    bundle.putParcelable(parcelableKey, parcelable);
                }

                fragment.setArguments(bundle);
            } else {
                if (serializable != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(serializableKey, serializable);
                    fragment.setArguments(bundle);
                }

                if (parcelable != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(parcelableKey, parcelable);
                    fragment.setArguments(bundle);
                }
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (fragmentTransactionAnimations != null) {
                fragmentTransaction.setCustomAnimations(
                        fragmentTransactionAnimations.getEnter(),
                        fragmentTransactionAnimations.getExit(),
                        fragmentTransactionAnimations.getPopEnter(),
                        fragmentTransactionAnimations.getPopExit()
                );
            }

            if (TransactionBehavior.ADD == transactionBehavior) {
                fragmentTransaction.add(fragmentContainerId, fragment, fragmentTag);
            } else {
                fragmentTransaction.replace(fragmentContainerId, fragment, fragmentTag);
            }

            /* By adding it to the back stack, you can return to the previous state by pressing the back button. */
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragmentTag);
            }

            /* clear back stack by tag */
            if (clearBackStackTillTag != null) {
                popTo(clearBackStackTillTag);
            }

            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * Push fragment in frame layout.
     *
     * @param fragment       fragment instance
     * @param isAdd          true : add fragment
     *                       false : replace fragment
     * @param addToBackStack true : if you want to add in back stack
     *                       false : not add in back stack
     */
    public void push(Fragment fragment, boolean isAdd, boolean addToBackStack) {
        if (isAdd) {
            add(fragment, addToBackStack);
        } else {
            replace(fragment, addToBackStack);
        }
    }

    /**
     * Pop last fragment in frame layout.
     */
    public void pop() {
        pop(false);
    }

    /**
     * Pop fragment.
     *
     * @param isImmediate True to pop immediately, false otherwise.
     */
    public void pop(boolean isImmediate) {
        if (isImmediate) {
            fragmentManager.popBackStackImmediate();
        } else {
            fragmentManager.popBackStack();
        }
    }

    /**
     * Pop to fragment.
     *
     * @param fragmentClass The class of fragment will be popped to.
     * @param isIncludeSelf True to include the fragment, false otherwise.
     */
    public void popTo(Class<? extends Fragment> fragmentClass, boolean isIncludeSelf) {
        popTo(fragmentClass, isIncludeSelf, false);
    }

    /**
     * Pop to fragment.
     *
     * @param fragmentClass The class of fragment will be popped to.
     * @param isIncludeSelf True to include the fragment, false otherwise.
     * @param isImmediate   True to pop immediately, false otherwise.
     */
    public void popTo(Class<? extends Fragment> fragmentClass, boolean isIncludeSelf, boolean isImmediate) {
        if (isImmediate) {
            fragmentManager.popBackStackImmediate(fragmentClass.getSimpleName(), isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        } else {
            fragmentManager.popBackStack(fragmentClass.getSimpleName(), isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }
    }

    /**
     * Close the all the fragment till the given tag name
     *
     * @param tag if tag name is null then all the fragment will be close
     *            or
     *            till the given tag name ( दिए गए टैग नाम तक )
     *
     *            Example : you add three fragment A, B, C, D then you provide fragment B tag then A & B fragment close.
     *            C & D remaining.
     *
     *            if provide tag name, then close all the fragment with the given tag name
     */
    public void popTo(String tag) {
        fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Pop all fragments.
     */
    public void popAll() {
        popAll(false);
    }

    /**
     * Pop all fragments.
     * Remove all entries from the back stack of this fragmentManager.
     */
    public void popAll(boolean isImmediate) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(0);
            if (isImmediate) {
                fragmentManager.popBackStackImmediate(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                fragmentManager.popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    /**
     * Close the number of fragments.
     *
     * @param numBackStack, number of fragments to pop up.
     */
    public void popBackStack(int numBackStack) {
        int fragCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < fragCount - numBackStack; i++) {
            fragmentManager.popBackStack();
        }
    }

    public int getBackStackEntryCount() {
        return fragmentManager.getBackStackEntryCount();
    }

    public boolean goBack() {
        int fragments = fragmentManager.getBackStackEntryCount();
        if (fragments > 1) {
            pop();
            System.out.println("CURRENT FRAGMENT ENTRY COUNT : " + fragments);
            return false;
        } else {
            System.out.println("SHOW EXIT ALERT DIALOG");
            return true;
        }
    }

    /**
     * Print all the added from list in logcat using tag System.
     */
    public void printActivityFragmentList() {
        /* Get all Fragment list */
        List<Fragment> fragmentList = fragmentManager.getFragments();
        System.out.println("printActivityFragmentList.size = " + fragmentList.size());
        for (Fragment fragment : fragmentList) {
            if (fragment != null) {
                String fragmentTag = fragment.getTag();
                System.out.println("printActivityFragmentList()=" + fragmentTag + "|fragment=" + fragment.getClass().getSimpleName());
            }
        }
        System.out.println("fragmentList.size()=" + fragmentList.size());
    }

    /**
     * Get exist Fragment by it's class name.
     *
     * @param fragmentClass
     * @return fragment
     */
    public Fragment getFragmentByClass(Class<? extends Fragment> fragmentClass) {
        return fragmentManager.findFragmentByTag(fragmentClass.getSimpleName());
    }

    /**
     * Get exist Fragment by it's tag name.
     *
     * @param tag
     * @return fragment
     */
    public Fragment getFragmentByTagName(String tag) {
        /* Get all Fragment list */
        List<Fragment> fragmentList = fragmentManager.getFragments();
        System.out.println("getFragmentByTagName.size = " + fragmentList.size());

        for (Fragment fragment : fragmentList) {
            if (fragment != null) {
                String fragmentTag = fragment.getTag();
                System.out.println("getFragmentByTagName()=" + fragmentTag + "|fragment=" + fragment.getClass().getSimpleName());

                /* If Fragment tag name is equal then return it. */
                assert fragmentTag != null;
                if (fragmentTag.equals(tag)) {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * Remove single fragment by class name
     *
     * @param fragmentClass
     */
    public void removeFragmentByClass(Class<? extends Fragment> fragmentClass) {
        try {
            for (int i = fragmentManager.getBackStackEntryCount() - 1; i >= 0; i--) {
                String backEntry = fragmentManager.getBackStackEntryAt(i).getName();
                System.out.println("fragmentManager.backEntry=" + backEntry);

                assert backEntry != null;
                if (backEntry.equals(fragmentClass.getSimpleName())) {
                    fragmentManager.popBackStack(fragmentClass.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    break;
                }
            }
        } catch (Exception exception) {
            System.out.print("Pop back stack error : " + exception);
            exception.printStackTrace();
        }
    }

    public Fragment getVisibleFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    private enum TransactionBehavior {
        REPLACE,
        ADD
    }
}
