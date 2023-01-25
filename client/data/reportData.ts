interface reportDataType {
  value: string;
  explain: string;
}

export const reportData: Array<reportDataType> = [
  {
    value: 'UNCORRECT',
    explain: '주제와 맞지 않은 게시물',
  },
  {
    value: 'DUPLICATION',
    explain: '도배 및 중복된 게시물',
  },
  {
    value: 'AD',
    explain: '과한 광고성 게시물',
  },
  {
    value: 'ABUSE',
    explain: '욕설 및 비방 게시물',
  },
  {
    value: 'OBSCENE',
    explain: '외설적인 게시물',
  },
  {
    value: 'PERSONALINFO',
    explain: '개인정보노출',
  },
  {
    value: 'ETC',
    explain: '기타',
  },
];
