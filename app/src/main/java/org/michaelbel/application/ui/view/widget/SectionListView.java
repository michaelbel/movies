package org.michaelbel.application.ui.view.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

@SuppressWarnings("all")
public class SectionListView extends ListView {

	public interface PinnedSectionListAdapter extends ListAdapter {
		boolean isItemViewTypePinned(int viewType);
	}

	public static class PinnedSection {
		public View view;
		public int position;
		public long id;
	}

    private final Rect mTouchRect = new Rect();
    private final PointF mTouchPoint = new PointF();
    private int mTouchSlop;
    private View mTouchTarget;
    private MotionEvent mDownEvent;

    private GradientDrawable mShadowDrawable;
    private int mSectionsDistanceY;
    private int mShadowHeight;

    private OnScrollListener mDelegateOnScrollListener;
    private PinnedSection mRecycleSection;
    private PinnedSection mPinnedSection;
    private int mTranslateY;

	private final OnScrollListener mOnScrollListener = new OnScrollListener() {

		@Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (mDelegateOnScrollListener != null) {
				mDelegateOnScrollListener.onScrollStateChanged(view, scrollState);
			}
		}

		@Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mDelegateOnScrollListener != null) {
                mDelegateOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            ListAdapter adapter = getAdapter();
            if (adapter == null || visibleItemCount == 0) {
                return;
            }

            boolean isFirstVisibleItemSection = isItemViewTypePinned(adapter, adapter.getItemViewType(firstVisibleItem));
            if (isFirstVisibleItemSection) {
                View sectionView = getChildAt(0);
                if (sectionView.getTop() == getPaddingTop()) {
                    destroyPinnedShadow();
                } else {
                    ensureShadowForPosition(firstVisibleItem, firstVisibleItem, visibleItemCount);
                }
            } else {
                int sectionPosition = findCurrentSectionPosition(firstVisibleItem);
                if (sectionPosition > -1) {
                    ensureShadowForPosition(sectionPosition, firstVisibleItem, visibleItemCount);
                } else {
                    destroyPinnedShadow();
                }
            }
		}
	};

    private final DataSetObserver mDataSetObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            recreatePinnedShadow();
        }

        @Override
        public void onInvalidated() {
            recreatePinnedShadow();
        }
    };

    public SectionListView(Context context) {
        super(context);
        initialize(context);
    }

    public SectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public SectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        setOnScrollListener(mOnScrollListener);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initShadow(true);
        setShadowVisible(false);
    }

    public void setShadowVisible(boolean visible) {
        initShadow(visible);
        if (mPinnedSection != null) {
            View v = mPinnedSection.view;
            invalidate(v.getLeft(), v.getTop(), v.getRight(), v.getBottom() + mShadowHeight);
        }
    }

    public void initShadow(boolean visible) {
        if (visible) {
            if (mShadowDrawable == null) {
                mShadowDrawable = new GradientDrawable(Orientation.TOP_BOTTOM,
                        new int[] { Color.parseColor("#ffa0a0a0"), Color.parseColor("#50a0a0a0"), Color.parseColor("#00a0a0a0")});
                mShadowHeight = (int) (8 * getResources().getDisplayMetrics().density);
            }
        } else {
            if (mShadowDrawable != null) {
                mShadowDrawable = null;
                mShadowHeight = 0;
            }
        }
    }

	private void createPinnedShadow(int position) {
		PinnedSection pinnedShadow = mRecycleSection;
		mRecycleSection = null;

		if (pinnedShadow == null) {
            pinnedShadow = new PinnedSection();
        }

		View pinnedView = getAdapter().getView(position, pinnedShadow.view, SectionListView.this);

		ViewGroup.LayoutParams layoutParams = pinnedView.getLayoutParams();
		if (layoutParams == null) {
		        layoutParams = generateDefaultLayoutParams();
		        pinnedView.setLayoutParams(layoutParams);
		}

		int heightMode = MeasureSpec.getMode(layoutParams.height);
		int heightSize = MeasureSpec.getSize(layoutParams.height);

		if (heightMode == MeasureSpec.UNSPECIFIED) heightMode = MeasureSpec.EXACTLY;

		int maxHeight = getHeight() - getListPaddingTop() - getListPaddingBottom();
		if (heightSize > maxHeight) heightSize = maxHeight;

		int ws = MeasureSpec.makeMeasureSpec(getWidth() - getListPaddingLeft() - getListPaddingRight(), MeasureSpec.EXACTLY);
		int hs = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
		pinnedView.measure(ws, hs);
		pinnedView.layout(0, 0, pinnedView.getMeasuredWidth(), pinnedView.getMeasuredHeight());
		mTranslateY = 0;

		pinnedShadow.view = pinnedView;
		pinnedShadow.position = position;
		pinnedShadow.id = getAdapter().getItemId(position);

		mPinnedSection = pinnedShadow;
	}

	private void destroyPinnedShadow() {
	    if (mPinnedSection != null) {
	        mRecycleSection = mPinnedSection;
	        mPinnedSection = null;
	    }
	}

    private void ensureShadowForPosition(int sectionPosition, int firstVisibleItem, int visibleItemCount) {
        if (visibleItemCount < 2) {
            destroyPinnedShadow();
            return;
        }

        if (mPinnedSection != null && mPinnedSection.position != sectionPosition) {
            destroyPinnedShadow();
        }

        if (mPinnedSection == null) {
            createPinnedShadow(sectionPosition);
        }

        int nextPosition = sectionPosition + 1;
        if (nextPosition < getCount()) {
            int nextSectionPosition = findFirstVisibleSectionPosition(nextPosition, visibleItemCount - (nextPosition - firstVisibleItem));
            if (nextSectionPosition > -1) {
                View nextSectionView = getChildAt(nextSectionPosition - firstVisibleItem);
                int bottom = mPinnedSection.view.getBottom() + getPaddingTop();
                mSectionsDistanceY = nextSectionView.getTop() - bottom;
                if (mSectionsDistanceY < 0) {
                    mTranslateY = mSectionsDistanceY;
                } else {
                    mTranslateY = 0;
                }
            } else {
                mTranslateY = 0;
                mSectionsDistanceY = Integer.MAX_VALUE;
            }
        }

    }

	private int findFirstVisibleSectionPosition(int firstVisibleItem, int visibleItemCount) {
		ListAdapter adapter = getAdapter();

        int adapterDataCount = adapter.getCount();
        if (getLastVisiblePosition() >= adapterDataCount) {
            return -1;
        }

        if (firstVisibleItem+visibleItemCount >= adapterDataCount){
            visibleItemCount = adapterDataCount-firstVisibleItem;
        }

		for (int childIndex = 0; childIndex < visibleItemCount; childIndex++) {
			int position = firstVisibleItem + childIndex;
			int viewType = adapter.getItemViewType(position);
			if (isItemViewTypePinned(adapter, viewType)) {
                return position;
            }
		}

		return -1;
	}

	private int findCurrentSectionPosition(int fromPosition) {
		ListAdapter adapter = getAdapter();

		if (fromPosition >= adapter.getCount()) {
            return -1;
        }

		if (adapter instanceof SectionIndexer) {
			SectionIndexer indexer = (SectionIndexer) adapter;
			int sectionPosition = indexer.getSectionForPosition(fromPosition);
			int itemPosition = indexer.getPositionForSection(sectionPosition);
			int typeView = adapter.getItemViewType(itemPosition);
			if (isItemViewTypePinned(adapter, typeView)) {
				return itemPosition;
			}
		}

		for (int position=fromPosition; position>=0; position--) {
			int viewType = adapter.getItemViewType(position);
			if (isItemViewTypePinned(adapter, viewType)) {
                return position;
            }
		}

		return -1;
	}

	private void recreatePinnedShadow() {
	    destroyPinnedShadow();
        ListAdapter adapter = getAdapter();

        if (adapter != null && adapter.getCount() > 0) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int sectionPosition = findCurrentSectionPosition(firstVisiblePosition);
            if (sectionPosition == -1) {
                return;
            }

            ensureShadowForPosition(sectionPosition, firstVisiblePosition, getLastVisiblePosition() - firstVisiblePosition);
        }
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		if (listener == mOnScrollListener) {
			super.setOnScrollListener(listener);
		} else {
			mDelegateOnScrollListener = listener;
		}
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		super.onRestoreInstanceState(state);
		post(this :: recreatePinnedShadow);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (adapter != null) {
			if (!(adapter instanceof PinnedSectionListAdapter))
				throw new IllegalArgumentException("Does your adapter implement PinnedSectionListAdapter?");
			if (adapter.getViewTypeCount() < 2)
				throw new IllegalArgumentException("Does your adapter handle at least two types" +
						" of views in getViewTypeCount() method: items and sections?");
		}

		ListAdapter oldAdapter = getAdapter();
		if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
		if (adapter != null) {
            adapter.registerDataSetObserver(mDataSetObserver);
        }
		if (oldAdapter != adapter) {
            destroyPinnedShadow();
        }

		super.setAdapter(adapter);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    super.onLayout(changed, l, t, r, b);
        if (mPinnedSection != null) {
            int parentWidth = r - l - getPaddingLeft() - getPaddingRight();
            int shadowWidth = mPinnedSection.view.getWidth();

            if (parentWidth != shadowWidth) {
                recreatePinnedShadow();
            }
        }
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mPinnedSection != null) {
			int pLeft = getListPaddingLeft();
			int pTop = getListPaddingTop();
			View view = mPinnedSection.view;
			canvas.save();
			int clipHeight = view.getHeight() + (mShadowDrawable == null ? 0 : Math.min(mShadowHeight, mSectionsDistanceY));
			canvas.clipRect(pLeft, pTop, pLeft + view.getWidth(), pTop + clipHeight);
			canvas.translate(pLeft, pTop + mTranslateY);
			drawChild(canvas, mPinnedSection.view, getDrawingTime());

			if (mShadowDrawable != null && mSectionsDistanceY > 0) {
			    mShadowDrawable.setBounds(mPinnedSection.view.getLeft(),
			            mPinnedSection.view.getBottom(),
			            mPinnedSection.view.getRight(),
			            mPinnedSection.view.getBottom() + mShadowHeight);
			    mShadowDrawable.draw(canvas);
			}

			canvas.restore();
		}
	}

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();
        final int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN
                && mTouchTarget == null
                && mPinnedSection != null
                && isPinnedViewTouched(mPinnedSection.view, x, y)) {

            mTouchTarget = mPinnedSection.view;
            mTouchPoint.x = x;
            mTouchPoint.y = y;
            mDownEvent = MotionEvent.obtain(ev);
        }

        if (mTouchTarget != null) {
            if (isPinnedViewTouched(mTouchTarget, x, y)) {
                mTouchTarget.dispatchTouchEvent(ev);
            }

            if (action == MotionEvent.ACTION_UP) {
                super.dispatchTouchEvent(ev);
                performPinnedItemClick();
                clearTouchTarget();
            } else if (action == MotionEvent.ACTION_CANCEL) {
                clearTouchTarget();
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (Math.abs(y - mTouchPoint.y) > mTouchSlop) {
                    MotionEvent event = MotionEvent.obtain(ev);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    mTouchTarget.dispatchTouchEvent(event);
                    event.recycle();
                    super.dispatchTouchEvent(mDownEvent);
                    super.dispatchTouchEvent(ev);
                    clearTouchTarget();
                }
            }
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isPinnedViewTouched(View view, float x, float y) {
        view.getHitRect(mTouchRect);
        mTouchRect.top += mTranslateY;
        mTouchRect.bottom += mTranslateY + getPaddingTop();
        mTouchRect.left += getPaddingLeft();
        mTouchRect.right -= getPaddingRight();
        return mTouchRect.contains((int)x, (int)y);
    }

    private void clearTouchTarget() {
        mTouchTarget = null;
        if (mDownEvent != null) {
            mDownEvent.recycle();
            mDownEvent = null;
        }
    }

    private boolean performPinnedItemClick() {
        if (mPinnedSection == null) {
            return false;
        }

        OnItemClickListener listener = getOnItemClickListener();
        if (listener != null && getAdapter().isEnabled(mPinnedSection.position)) {
            View view =  mPinnedSection.view;
            playSoundEffect(SoundEffectConstants.CLICK);
            if (view != null) {
                view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
            }
            listener.onItemClick(this, view, mPinnedSection.position, mPinnedSection.id);
            return true;
        }
        return false;
    }

    public static boolean isItemViewTypePinned(ListAdapter adapter, int viewType) {
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) adapter).isItemViewTypePinned(viewType);
    }
}