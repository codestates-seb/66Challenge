import axios from 'axios';
import { getCookie } from './cookies';
import type {
  postReportGeneralProps,
  postReportAuthPorps,
  postReportReviewPorps,
} from './moduleInterface';

export async function postHabitReport({
  habitId,
  userId,
  reportType,
}: postReportGeneralProps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SEVER_URL}/habits/${habitId}/reports`,
        { habitId, reportType, reporter: userId },
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function postAuthReport({
  habitId,
  authId,
  reportType,
  userId,
}: postReportAuthPorps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/${authId}/reports`,
        { authId, reportType, reporter: userId },
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function postReviewReport({
  habitId,
  reviewId,
  reportType,
  userId,
}: postReportReviewPorps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}/reports`,
        { reviewId, reportType, reporter: userId },
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
