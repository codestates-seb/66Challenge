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
  cookie: string;
  userId: string;
}

interface PatchUserInfoProps extends UserGeneralProps {
  username: string;
  password: string;
}

interface getUserCertificateProps extends UserGeneralProps {
  habitId: string;
}
// userFunctionModule

// habitFunctionModule
interface deleteHabitProps {
  cookie: string;
  habitId: string;
}

interface habitGeneralProps extends deleteHabitProps {
  userId: string;
}

interface PostHabitData extends HabitFormValues {
  habitImage: string;
}

interface postHabitProps {
  data: PostHabitData;
  cookie: string;
}

interface getHabitAuthsReviewsProps extends pageAndSize {
  habitId: string;
}

interface postAuthReportProps extends deleteHabitProps {
  authReportPostDto: string;
  authId: string;
}

interface postHabitReviewProps extends habitGeneralProps {
  body: string;
  score: number;
}

interface deleteHabitReviewProps extends deleteHabitProps {
  reviewId: string;
}

interface patchHabitReviewProps extends deleteHabitReviewProps {
  body: string;
  score: number;
}

interface getHabitsSearchProps extends pageAndSize {
  keyword: string;
}

interface getHabitsSearchCategoryProps extends pageAndSize {
  categoryId: string;
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
