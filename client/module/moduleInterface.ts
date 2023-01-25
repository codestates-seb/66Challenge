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

interface getHabitAuthsReviewsProps extends pageAndSize {
  habitId: number;
}

interface postAuthReportProps extends deleteHabitProps {
  authReportPostDto: string;
  authId: number;
}

interface postHabitReviewProps extends habitGeneralProps {
  body: string;
  score: number;
}

interface deleteHabitReviewProps extends deleteHabitProps {
  reviewId: number;
}

interface patchHabitReviewProps extends deleteHabitReviewProps {
  body: string;
  score: number;
}

interface getHabitsSearchProps extends pageAndSize {
  keyword: string;
}

interface getHabitsSearchCategoryProps extends pageAndSize {
  categoryId: number;
}
// habitFunctionModule

export type {
  SignUpProps,
  UserGeneralProps,
  PatchUserInfoProps,
  getUserCertificateProps,
  habitGeneralProps,
  postHabitProps,
  deleteHabitProps,
  getHabitAuthsReviewsProps,
  postAuthReportProps,
  postHabitReviewProps,
  deleteHabitReviewProps,
  patchHabitReviewProps,
  getHabitsSearchProps,
  getHabitsSearchCategoryProps,
};
