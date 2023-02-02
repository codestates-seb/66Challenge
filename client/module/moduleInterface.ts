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
  age: string;
  gender: string;
}

interface UserGeneralProps {
  userId: number | null;
  password?: string | null;
}

interface PatchUserInfoProps extends UserGeneralProps {
  username?: string | null;
  password?: string | null;
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
  userId?: number;
}

interface postHabitProps {
  data: HabitFormValues;
}

interface getHabitsSearchProps extends pageAndSize {
  keyword: string;
}

interface getHabitsSearchCategoryProps extends pageAndSize {
  categoryId: number;
}

interface getHabitsInHomeProps {
  page?: string;
  size: string;
  type: string;
  userId?: number;
}

interface patchHabitDetailProps extends habitGeneralProps {
  data: unknown;
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

interface profileImageProps {
  userId: number;
  profileImage: File | null;
}

interface activeCategories {
  categoryId: number;
  type: string;
}

interface activeChallenges {
  challengeId: number;
  progressDays: number;
  habitId: number;
  subTitle: string;
}

interface daysOfFailList {
  habitId: number;
  habitTitle: string;
  createdAt: string;
  daysOfFail: number;
}

interface favoriteCategories {
  count: number;
  categoryId: number;
}

interface numOfAuthByChallengeList {
  habitId: numOfAuthByChallengeList;
  habitTitle: string;
  createdAt: string;
  numOfAuth: number;
  numOfUsedWildCard: number;
}
interface UserInfoType {
  activeCategories: Array<activeCategories>;
  activeChallenges: Array<activeChallenges>;
  averageDaysOfFail: number;
  biggestProgressDays: number;
  daysOfFailList: Array<daysOfFailList>;
  email: string;
  favoriteCategories: Array<favoriteCategories>;
  numOfAuthByChallengeList: Array<numOfAuthByChallengeList>;
  profileImageUrl: string | null;
  userId: number;
  username: string;
  numOfBookmarkHabit: number;
  numOfHostHabit: number;
}

export type {
  SignUpProps,
  UserGeneralProps,
  PatchUserInfoProps,
  getUserCertificateProps,
  habitGeneralProps,
  postHabitProps,
  patchHabitDetailProps,
  deleteHabitProps,
  getReviewsProps,
  postHabitReviewProps,
  deleteHabitReviewProps,
  patchHabitReviewProps,
  getHabitsSearchProps,
  getHabitsSearchCategoryProps,
  getHabitsInHomeProps,
  postReportGeneralProps,
  postReportHabitProps,
  postReportAuthPorps,
  postReportReviewPorps,
  getAuthProps,
  deleteAuthProps,
  profileImageProps,
  UserInfoType,
};
