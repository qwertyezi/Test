package com.yezi.text.widget;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yezi.text.R;
import com.yezi.text.utils.InputMethodUtil;

public class SearchView extends FrameLayout {

    public static final int LAYOUT_TRANSITION_DURATION = 300;

    private LinearLayout mSearchBar;
    private ImageView mSearchButton;
    private ExtendedEditText mSearchSrc;
    private ImageView mCloseButton;
    private TransitionDrawable mSearchBarBackground;
    private boolean mExpanded;

    private boolean mClearOnClose = true;

    private OnToggleListener mOnToggleListener;

    private OnQueryTextListener mOnQueryTextListener;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_search_view, this);

        mSearchBar = (LinearLayout) findViewById(R.id.search_bar);
        mSearchButton = (ImageView) findViewById(R.id.search_button);
        mSearchSrc = (ExtendedEditText) findViewById(R.id.search_src_text);
        mCloseButton = (ImageView) findViewById(R.id.search_close_btn);

        mSearchBarBackground = (TransitionDrawable) mSearchBar.getBackground();
        mSearchButton.setOnClickListener(mOnClickListener);
        mCloseButton.setOnClickListener(mOnClickListener);

        mSearchSrc.addTextChangedListener(mTextWatcher);
        mSearchSrc.setOnEditorActionListener(mOnEditorActionListener);
        mSearchSrc.setOnBackKeyListener(mOnBackKeyListener);
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    private final OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.search_button) {
                onSearchClicked();
            } else if (v.getId() == R.id.search_close_btn) {
                onCloseClicked();
            }
        }
    };

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            SearchView.this.onTextChanged(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            onSubmitQuery();
            return true;
        }
    };

    private final ExtendedEditText.onBackKeyListener mOnBackKeyListener = new ExtendedEditText.onBackKeyListener() {
        @Override
        public boolean onBackKey() {
            toggle(false);
            return true;
        }
    };


    private void onTextChanged(CharSequence newText) {

        if (mOnQueryTextListener != null) {
            mOnQueryTextListener.onQueryTextChange(newText.toString());
        }
    }

    private void onSubmitQuery() {
        String query = mSearchSrc.getText().toString();
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            if (mOnQueryTextListener == null
                    || !mOnQueryTextListener.onQueryTextSubmit(query)) {
                toggle(false);
            }
        }
    }

    private void onCloseClicked() {
        toggle(false);
    }

    private void onSearchClicked() {
        toggle(true);
    }

    public void toggle(boolean expanded) {
        mExpanded = expanded;

        ViewGroup.LayoutParams lp = mSearchBar.getLayoutParams();
        lp.width = expanded ? LayoutParams.MATCH_PARENT : LayoutParams.WRAP_CONTENT;
        mSearchBar.setLayoutParams(lp);

        if (expanded) {
            mSearchBarBackground.startTransition(LAYOUT_TRANSITION_DURATION);
        } else {
            mSearchBarBackground.reverseTransition(LAYOUT_TRANSITION_DURATION);
        }

        mSearchButton.setEnabled(!expanded);

        int visCollapsible = expanded ? VISIBLE : GONE;
        mSearchSrc.setVisibility(visCollapsible);
        mCloseButton.setVisibility(visCollapsible);

        InputMethodUtil.toggleSoftKeyboard(mSearchSrc, expanded);

        if (mClearOnClose) {
            clear();
        }

        if (mOnToggleListener != null) {
            mOnToggleListener.onToggle(expanded);
        }
    }

    private void clear() {
        mSearchSrc.setText(null);
    }

    public void setClearOnClose(boolean clearOnClose) {
        mClearOnClose = clearOnClose;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        mOnQueryTextListener = onQueryTextListener;
    }

    public void setOnToggleListener(OnToggleListener onToggleListener) {
        mOnToggleListener = onToggleListener;
    }

    public interface OnToggleListener {
        void onToggle(boolean expanded);
    }

    public interface OnQueryTextListener {
        boolean onQueryTextSubmit(String query);

        boolean onQueryTextChange(String newText);
    }
}
