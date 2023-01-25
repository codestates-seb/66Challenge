import type { HabitFormValues } from '../pages/habit/post';

// general
interface pageAndSize {
  page: string;
  size: string;
}
// general

// userFunctionModule
interface SignUpProps {
  email: string;
  username: string;
  password: string;
}

interface UserGeneralProps {
  userId: number | null;
  password?: string;
}

interface PatchUserInfoProps extends UserGeneralProps {
  username: string;
  password: string;
}

interface getUserCertificateProps extends UserGeneralProps {
  habitId: number;
}
// userFunctionModule

// habitFunctionModule
interface deleteHabitProps {
  habitId: number;
}

interface habitGeneralProps extends deleteHabitProps {
  userId: number;
}

interface PostHabitData extends HabitFormValues {
  habitImage: File | null;
  successImage: File | null;
  failImage: File | null;
}

interface postHabitProps {
  data: PostHabitData;
}

interface getHabitsSearchProps extends pageAndSize {
  keyword: string;
}

interface getHabitsSearchCategoryProps extends pageAndSize {
  categoryId: number;
}
// habitFunctionModule

// reviewFunctionModule
interface getReviewsProps extends pageAndSize {
  habitId: number;
}

interface postHabitReviewProps {
  habitId: number;
  userId: number;
  body: string;
  score: number;
}

interface deleteHabitReviewProps {
  reviewId: number;
  habitId: number;
}

interface patchHabitReviewProps extends deleteHabitReviewProps {
  body: string;
  score: number;
}

// reviewFunctionModule

// authFunctionModule
interface getAuthProps extends pageAndSize {
  habitId: number;
}

interface deleteAuthProps {
  authId: number;
}

// authFunctionModule

// reportFunctionModule
interface postReportGeneralProps {
  habitId: number;
  userId: number;
  reportType: string;
}

interface postReportHabitProps extends postReportGeneralProps {
  hostUserId: number;
}

interface postReportAuthPorps extends postReportGeneralProps {
  authId: number;
  authorUserId: number;
}

interface postReportReviewPorps extends postReportGeneralProps {
  reviewId: number;
  reviewerUserId: number;
}
// reportFunctionModule

export type {
  SignUpProps,
  UserGeneralProps,
  PatchUserInfoProps,
  getUserCertificateProps,
  habitGeneralProps,
  postHabitProps,
  deleteHabitProps,
  getReviewsProps,
  postHabitReviewProps,
  deleteHabitReviewProps,
  patchHabitReviewProps,
  getHabitsSearchProps,
  getHabitsSearchCategoryProps,
  postReportGeneralProps,
  postReportHabitProps,
  postReportAuthPorps,
  postReportReviewPorps,
  getAuthProps,
  deleteAuthProps,
};
