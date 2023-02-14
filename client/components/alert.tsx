import { useEffect, Dispatch, SetStateAction } from 'react';

interface AlertStatusType {
  isOpen: boolean;
  message: string;
  icon?: string | React.ReactNode;
}

interface AlertPropsType {
  alertStatus: AlertStatusType;
  setAlertStatus: Dispatch<SetStateAction<AlertStatusType>>;
  time?: number;
}

export function Alert({ alertStatus, setAlertStatus, time }: AlertPropsType) {
  useEffect(() => {
    setTimeout(() => {
      if (alertStatus.isOpen) {
        setAlertStatus({ ...alertStatus, isOpen: false });
      }
    }, time || 3000);
  }, [alertStatus.isOpen]);

  const alertBoxClassName = alertStatus.isOpen ? 'flex' : 'hidden';

  return (
    <div
      className={`alert-container fixed mx-auto top-5 justify-center left-1/2 -translate-x-1/2 z-20  ${alertBoxClassName}`}
    >
      <div className="alert-wrapper py-2 px-4 flex justify-center items-center gap-2 animate-dropDown bg-mainColor text-white border-[1px] border-bordercolor rounded shadow-lg">
        <div className="alert-icon">{alertStatus.icon}</div>
        <div className="alert-message text-sm pt-[2px]">
          <span>{alertStatus.message}</span>
        </div>
      </div>
    </div>
  );
}
