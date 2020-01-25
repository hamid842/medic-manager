import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { getAllEntities } from './medicine-info.reducer';
import { Col, FormGroup, Input } from 'reactstrap';
import { RouteComponentProps } from 'react-router-dom';

export interface IMedicineInfoOptionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SelectGroupList = (props: IMedicineInfoOptionsProps) => {
  const { medicineInfoList1 } = props;
  const getAllData = () => {
    props.getAllEntities();
  };

  useEffect(() => {
    getAllData();
  }, []);

  const selectedItems = medicineInfoList1.map(item => {
    return <option key={item.id}>{item.name}</option>;
  });

  //   const handleChange = e => {
  //     if (e.target.value === 'All') {
  //       // window.location.reload(false);
  //       return all;
  //     } else {
  //       const filteredData = medicineInfoList.filter(el => el.name.includes(e.target.value));
  //       const idOf = filteredData[0].id;
  //       setId(idOf);
  //     }
  //   };

  return (
    <FormGroup row>
      <Col sm={10}>
        <Input
          type="select"
          name="select"
          id="exampleSelect"
          //   onChange={handleChange}
          // onChange={e => {
          //   e.preventDefault();
          //   const filteredData = medicineInfoList.filter(el => el.name.includes(e.target.value));
          //   const idOf = filteredData[0].id;
          //   setId(idOf);
          // }}
        >
          <option>All</option>
          {selectedItems}
        </Input>
      </Col>
    </FormGroup>
  );
};

const mapStateToProps = ({ medicineInfo }) => ({
  medicineInfoList1: medicineInfo.entities
});

const mapDispatchToProps = {
  getAllEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SelectGroupList);
